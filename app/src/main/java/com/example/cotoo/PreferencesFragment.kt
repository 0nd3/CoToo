package com.example.cotoo

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.cotoo.databinding.FragmentPreferencesBinding
import android.os.LocaleList
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import java.util.*


class PreferencesFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val localeList =  listOf("de", "en", "fi", "sv")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        val view = binding.root

        // yötilaswitchin listeneri

        binding.ModeSwitch.setOnCheckedChangeListener { _, b ->
            when (b) {
                true ->  {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    viewModel.setTheme(true)
                }

                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    viewModel.setTheme(false)
                }
            }
        }
        val isNightModeOn: Boolean = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        binding.ModeSwitch.isChecked = isNightModeOn


        // spinnerin listeneri
        // Kielet toimii, mutta restartin jälkeen kun menee ekan kerran tähän fragmenttiin,
        // spinneri valitsee listan ekan kielen, joka on saksa, asettaen sen valituksi kieleksi.
        // setselection ohittaa localemuutoksen, mutta väärä arvo edelleen näkyy valittuna.
        // ... pitäs korjata ...

        binding.LangSpinner.setSelection(0,false)

        binding.LangSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    setAppLocale(Locale.forLanguageTag(localeList[p2]))
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setAppLocale(locale: Locale) {
        val localeManager = requireActivity().getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = LocaleList(locale)
    }
}