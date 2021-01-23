package com.example.mynote;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynote.data.LabelData;
import com.example.mynote.databinding.ActivityMainBinding;
import com.example.mynote.model.EditLabelViewModel;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_home, R.id.subnav_label_edit)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupWithNavController(binding.navView, navController);

        initEditLabelRequirement();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public void setNavigationWithToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void initEditLabelRequirement() {
        AtomicInteger order = new AtomicInteger(1);

        EditLabelViewModel model = new ViewModelProvider(this).get(EditLabelViewModel.class);

        Menu labelMenu = binding.navView.getMenu().findItem(R.id.nav_label_container).getSubMenu();

        model.getAllLabel().observe(this, list -> {

            for (LabelData data: list) {
                String name = data.getName();

                labelMenu
                        .add(
                                R.id.submenu_label_container,
                                R.id.nav_home,
                                order.getAndIncrement(),
                                name
                        )
                        .setIcon(R.drawable.ic_outline_label_24)
                        .setTitle(name);
            }
        });
    }
}