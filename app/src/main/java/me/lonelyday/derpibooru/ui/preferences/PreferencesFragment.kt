package me.lonelyday.derpibooru.ui.preferences

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import me.lonelyday.derpibooru.R

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {
    private val viewModel by viewModels<PreferencesViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<EditTextPreference>("key")?.setOnPreferenceChangeListener { preference, newValue ->
            lifecycleScope.launchWhenStarted {
                if (viewModel.checkKey(newValue as String)) {
                    (preference as EditTextPreference).text = newValue as String
                    showDialog("Success!")
                } else {
                    showDialog("Wrong api key!")
                }
            }
            false
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .show()
    }
}