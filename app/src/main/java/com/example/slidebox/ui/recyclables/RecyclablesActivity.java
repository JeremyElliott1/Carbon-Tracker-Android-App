package com.example.slidebox.ui.recyclables;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slidebox.MainActivity;
import com.example.slidebox.R;


public class RecyclablesActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclables_activity);
    }

    public void paperPopup(View v) {
        PopupMenu paperPopup = new PopupMenu(this, v);
        paperPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        paperPopup.inflate(R.menu.recyclables_popup);
        paperPopup.show();

    }

    public void plasticPopup(View v) {
        PopupMenu plasticPopup = new PopupMenu(this, v);
        plasticPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        plasticPopup.inflate(R.menu.recyclables_popup_plastic);
        plasticPopup.show();

    }

    public void metalPopup(View v) {
        PopupMenu metalPopup = new PopupMenu(this, v);
        metalPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        metalPopup.inflate(R.menu.recyclables_popup_metal);
        metalPopup.show();

    }

    public void glassPopup(View v) {
        PopupMenu glassPopup = new PopupMenu(this, v);
        glassPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        glassPopup.inflate(R.menu.recyclables_popup);
        glassPopup.show();

    }

    public void unsurePopup(View v) {
        PopupMenu unsurePopup = new PopupMenu(this, v);
        unsurePopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        unsurePopup.inflate(R.menu.recyclables_popup);
        unsurePopup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

            }
