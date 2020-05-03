package com.example.slidebox.ui.leaderboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RankingAdapter extends FirestorePagingAdapter<UserPoints, RankingAdapter.UserPointsViewHolder> {


    private static final String TAG = "RankingAdapter";
    private static final int TODAY_FLAG = 1;
    private static final int WEEK_FLAG = 2;
    private static final int MONTH_FLAG = 3;
    private int flag;
    private int count = 0;
    private OnThumbupClick onThumbupClick;
    private DocumentSnapshot ds;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RankingAdapter(@NonNull FirestorePagingOptions<UserPoints> options, int flag, OnThumbupClick onThumbupClick) {
        super(options);
        this.flag = flag;
        this.onThumbupClick = onThumbupClick;
    }

    @NonNull
    @Override
    public UserPointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rangkinglist, parent, false);


        return new UserPointsViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final UserPointsViewHolder holder, int position, @NonNull UserPoints model) {
        String fullNameDisplay = model.getFirstName() + "  " + model.getLastName();
        String point = null;
        switch (flag) {
            case TODAY_FLAG:
                point = String.valueOf(model.getTotalPoints());
                break;
            case WEEK_FLAG:
                point = String.valueOf(model.getWeeklyPoints());
                break;
            case MONTH_FLAG:
                point = String.valueOf(model.getMonthlyPoints());
                break;
        }
        //holder.onClick(model.getNumThumbup());
        holder.getFullName().setText(fullNameDisplay);
        holder.getImageViewThumbup().setImageResource(R.drawable.ic_thumb_up_black_24dp);
        holder.getItemPosition().setText(String.valueOf(position + 1));
        holder.getPoints().setText(point);
        holder.getNumThumbup().setText(String.valueOf(model.getNumThumbup()));

        DocumentSnapshot dsPosition = getItem(position);
        String fn = (String) dsPosition.get("firstName") + (String) dsPosition.get("lastName");
    }

    private void readDocSnapshot() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("users").document("thumbup").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ds = document;
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {
            case LOADED:
                Log.d(TAG, "All item loaded " + getItemCount());
                break;
        }
    }

    class UserPointsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String TAG = "Holder";
        private TextView fullName;
        private TextView points;
        private ImageView imageViewThumbup;
        private TextView numThumbup;
        private TextView itemPosition;
        private ImageView imageViewUser;


        public UserPointsViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.textViewUserName);
            points = itemView.findViewById(R.id.textViewPoints);
            imageViewThumbup = itemView.findViewById(R.id.imageViewThumbup);
            numThumbup = itemView.findViewById(R.id.textViewThumbupNum);
            itemPosition = itemView.findViewById(R.id.textViewPosition);
            imageViewUser = itemView.findViewById(R.id.imageViewUser);
            imageViewThumbup.setOnClickListener(this);

        }

        public TextView getItemPosition() {
            return itemPosition;
        }

        public ImageView getImageViewUser() {
            return imageViewUser;
        }

        public TextView getFullName() {
            return fullName;
        }

        public TextView getPoints() {
            return points;
        }

        public ImageView getImageViewThumbup() {
            return imageViewThumbup;
        }

        public TextView getNumThumbup() {
            return numThumbup;
        }

/*        public int onClick(final String o, final DocumentSnapshot documentSnapshot) {
            final int[] i1 = {Integer.valueOf(o)};
            imageViewThumbup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i1[0]++;
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    String fullname = user.getUid();


                    Log.d(TAG, "Count is " + i1[0]);
                    Map<String, Object> map = new HashMap<>();
                    map.put(fullname, String.valueOf(i1[0]));
                    firebaseFirestore.collection("users").document(user.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Added thumbup! ");
                        }
                    });
                    firebaseFirestore.collection("users").document("thumbup").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Added thumbup! ");
                        }
                    });
                }
            });
            return i1[0];
        }*/

        public void  changeNum(){
            DocumentSnapshot documentSnapshot = getItem(getAdapterPosition());
            String id = documentSnapshot.getId();
            addThumbupNumToUserCollection(id);
            addThumbupCollection(id);
            Log.d(TAG, "this in stage3");
            Log.d(TAG, "this in holder"+documentSnapshot.get("numThumbup",Integer.class));
            Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
          /*  int num =documentSnapshot.get("numThumbup",Integer.class).intValue();
            num++;
            Log.d(TAG, "Count is " + num);
            Map<String, Object> map = new HashMap<>();
            map.put("numThumbup", num);
            firebaseFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Added thumbup! ");
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            //onThumbupClick.onClick();
            DocumentSnapshot documentSnapshot = getItem(getAdapterPosition());
            final String id = documentSnapshot.getId();
            addThumbupNumToUserCollection(id);
            addThumbupCollection(id);
            Log.d(TAG, "this in stage0" + getAdapterPosition());
            changeNum();
            Log.d(TAG, "this in stage1");
            final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            firebaseFirestore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "this in stage2");
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "this in stage3");
                            Log.d(TAG, "this in holder"+document.get("numThumbup",Integer.class));
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            int num =document.get("numThumbup",Integer.class).intValue();
                            num++;
                            Log.d(TAG, "Count is " + num);
                            Map<String, Object> map = new HashMap<>();
                            map.put("numThumbup", num);

                            firebaseFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Added thumbup! ");
                                }
                            });
                            firebaseFirestore.collection("users").document("thumbup").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Added thumbup! ");
                                }
                            });
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        private void addThumbupNumToUserCollection(final String id) {
            firebaseFirestore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "this in add usercollection stage1");
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if (document.get("numThumbup") == null) {
                                int i = 0;
                                Map<String, Object> map = new HashMap<>();
                                map.put("numThumbup", i);
                                firebaseFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Added thumbup! ");
                                    }
                                });
                            }
                            ;
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        private void addThumbupCollection(final String id) {

            firebaseFirestore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if (document.get(user.getUid()) == null) {
                                int i = 0;
                                Map<String, Object> map = new HashMap<>();
                                map.put(user.getUid(), String.valueOf(i));
                                firebaseFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Added thumbup! ");
                                    }
                                });
                            }
                            ;
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

    }

public interface OnThumbupClick {
    void onClick();
    void onClick(DocumentSnapshot documentSnapshot ,int position);
}
}
