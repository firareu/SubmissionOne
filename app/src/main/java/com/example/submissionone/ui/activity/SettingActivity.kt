package com.example.submissionone.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.submissionone.R
import com.example.submissionone.databinding.ActivitySettingBinding
import com.example.submissionone.local.Setting
import com.example.submissionone.ui.model.FavViewModel
import com.example.submissionone.ui.model.FavViewModelFactory
import com.example.submissionone.ui.model.SettingViewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(Setting(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Setting"
        }


        viewModel.getTema().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.ivBright.setImageDrawable(ContextCompat.getDrawable(binding.ivBright.context, R.drawable.baseline_brightness_7_24_white))
                binding.ivNight.setImageDrawable(ContextCompat.getDrawable(binding.ivNight.context, R.drawable.baseline_bedtime_24_white))
                //darkmode(true)
                //brightmode(false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.ivBright.setImageDrawable(ContextCompat.getDrawable(binding.ivBright.context, R.drawable.baseline_brightness_7_24))
                binding.ivNight.setImageDrawable(ContextCompat.getDrawable(binding.ivNight.context, R.drawable.baseline_bedtime_24))
                //darkmode(false)
                //brightmode(true)
            }
            binding.settingTheme.isChecked = it
        }
        binding.settingTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTema(
                isChecked
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}