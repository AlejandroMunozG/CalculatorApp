package com.example.calculator


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import com.example.calculator.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private var isTaxFragmentDisplayed = false
    private var isSettingsFragmentDisplayed = false
    private var isHistoryFragmentDisplayed = false

    private val calcList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fullscreen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val nightModeEnabled = getNightModePreference()
        AppCompatDelegate.setDefaultNightMode(
            if (nightModeEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun appendToInput(text: String) {
            val currentText = binding.inputView.text.toString()
            binding.inputView.text = currentText + text
        }
        // Clear Button
        binding.clearAllBtn.setOnClickListener{
            binding.inputView.text = ""
            binding.outputView.text = ""
        }
        // Backspace Button
        binding.backBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            if (currentText.isNotEmpty()) {
                binding.outputView.text = ""
                val newText = currentText.substring(0, currentText.length - 1)
                binding.inputView.text = newText
            }
        }
        // Divide Button
        binding.divideBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()

            // Check if the last character is not already a '+'
            if (currentText.isNotEmpty() && currentText.last() != '/') {
                val newText = currentText + "/"
                binding.inputView.text = newText
            }
        }
        // Seven Button
        binding.sevenBtn.setOnClickListener {
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "7"
            binding.inputView.text = newText
        }
        // Eight Button
        binding.eightBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "8"
            binding.inputView.text = newText
        }
        // Nine Button
        binding.nineBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "9"
            binding.inputView.text = newText
        }
        // Multiply Button
        binding.multiplyBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()

            // Check if the last character is not already a '+'
            if (currentText.isNotEmpty() && currentText.last() != 'x') {
                val newText = currentText + "x"
                binding.inputView.text = newText
            }
        }
        // Four Button
        binding.fourBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "4"
            binding.inputView.text = newText
        }
        // Five Button
        binding.fiveBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "5"
            binding.inputView.text = newText
        }
        // Six Button
        binding.sixBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "6"
            binding.inputView.text = newText
        }
        // Minus Button
        binding.minusBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()

            // Check if the last character is not already a '+'
            if (currentText.isNotEmpty() && currentText.last() != '-') {
                val newText = currentText + "-"
                binding.inputView.text = newText
            }
        }
        // One Button
        binding.oneBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "1"
            binding.inputView.text = newText
        }
        // Two Button
        binding.twoBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "2"
            binding.inputView.text = newText
        }
        // Three Button
        binding.threeBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "3"
            binding.inputView.text = newText
        }
        // Plus Button
        binding.plusBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()

            // Check if the last character is not already a '+'
            if (currentText.isNotEmpty() && currentText.last() != '+') {
                val newText = currentText + "+"
                binding.inputView.text = newText
            }
        }
        // Zero Button
        binding.zeroBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val newText = currentText + "0"
            binding.inputView.text = newText
        }
        // Decimal Button
        binding.decimalBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            if (!currentText.contains(".")) {
                appendToInput(".")
            }
        }
        // Equal Button
        binding.equalBtn.setOnClickListener{
            performCalculation()
        }
        // set tax text to whatever tax user picked
        val userTAX = intent.getStringExtra("TAX")
        if (userTAX != null) binding.taxBtn.text = userTAX + "%"

        //tax button
        binding.taxBtn.setOnClickListener{
            val currentText = binding.inputView.text.toString()
            val taxValue = userTAX?.toFloat()?:0.0f
            val result = calculatePercentage(currentText,taxValue)
            binding.outputView.text = result.toString()
        }

        // nav bar clicking and etc
        val navView = findViewById<NavigationView>(R.id.navigation_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_Tax -> {
                    switchTaxFragment()
                    // Close the navigation drawer after item selection
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_Settings -> {
                    switchSettingsFragment()
                    // Close the navigation drawer after item selection
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_History -> {
                    switchHistoryFragment()
                    // Close the navigation drawer after item selection
                    binding.drawerLayout.closeDrawers()
                    true
                }
                // Handle other navigation items if needed
                else -> false
            }

        }



    }
    // calculates %
    private fun calculatePercentage(input: String, taxPercent: Float): Any {
        if (input.isEmpty()) {
            // Handle the case when input is empty (e.g., show an error message)
            return "Tax Entered"
        }

        val inputValue = input.toFloat()
        val percentage = (inputValue * taxPercent) / 100
        val result = inputValue + percentage

        calcList.add(result.toString())

        return result

    }
    // calculation funcs, order of operations, & outputs
    private fun performCalculation() {
        val result = calculateResults()
        binding.outputView.text = result

        calcList.add(result)


    }
    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculator(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculator(timesDivision)
        return result.toString()
        return ""
    }
    private fun addSubtractCalculator(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float
        for (i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                if (operator == '+'){
                    result += nextDigit
                }
                if (operator == '-'){
                    result -= nextDigit
                }
            }
        }

        return result
    }
    private fun timesDivisionCalculator(passedList:MutableList<Any>): MutableList<Any> {
        var list = passedList
        while(list.contains('x') || list.contains('/')){
            list = calcTimesDiv(list)
        }
        return list
    }
    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices){
            if(passedList[i] is Char && i !=passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float
                when(operator){
                    'x' ->{
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->{
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->{
                        newList.add(prevDigit)
                        newList.add(operator)
                    }

                }
            }
            if(i > restartIndex){
                newList.add(passedList[i])
            }

        }

        return newList
    }
    private fun digitsOperators(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in binding.inputView.text){
            if(character.isDigit() || character == '.')
                currentDigit+=character
            else{
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if(currentDigit != "") list.add(currentDigit.toFloat())

        return list
    }
    
    //opens nav
    fun openDrawer(view: View) {
        val drawerLayout = binding.drawerLayout
        drawerLayout.openDrawer(GravityCompat.START)
    }
    // History fragment
    private fun switchHistoryFragment(){
        clearPreviousFragment()

        val calcListFragment = sendToHistFrag(calcList)

        val mainContentLayout = findViewById<ConstraintLayout>(R.id.mainContentLayout)
        mainContentLayout.visibility = View.GONE

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, calcListFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        isHistoryFragmentDisplayed = true

        val navView = findViewById<NavigationView>(R.id.navigation_view)
        navView.menu.findItem(R.id.nav_History).isChecked = false
    }
    //Tax fragment
    private fun switchTaxFragment() {
        clearPreviousFragment()

        val mainContentLayout = findViewById<ConstraintLayout>(R.id.mainContentLayout)
        mainContentLayout.visibility = View.GONE
        val taxFragment = TaxFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, taxFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        isTaxFragmentDisplayed = true

        val navView =findViewById<NavigationView>(R.id.navigation_view)
        navView.menu.findItem(R.id.nav_Tax).isChecked =false

    }
    // Settings fragment
    private fun switchSettingsFragment(){
        clearPreviousFragment()

        val mainContentLayout = findViewById<ConstraintLayout>(R.id.mainContentLayout)
        mainContentLayout.visibility = View.GONE
        val settingsFragment = settings()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,settingsFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        isSettingsFragmentDisplayed = true

        val navView =findViewById<NavigationView>(R.id.navigation_view)
        navView.menu.findItem(R.id.nav_Settings).isChecked = false
    }
    // switches to back to main if back btn on phone is pressed
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navView = findViewById<NavigationView>(R.id.navigation_view)

        if (isTaxFragmentDisplayed) {
            switchToMainActivity()
            navView.menu.findItem(R.id.nav_Tax).isChecked = false
        } else if (isSettingsFragmentDisplayed) {
            switchToMainActivity()
            navView.menu.findItem(R.id.nav_Settings).isChecked = false
        } else if (isHistoryFragmentDisplayed) {
            switchToMainActivity()
            navView.menu.findItem(R.id.nav_History).isChecked = false
        } else {
            super.onBackPressed()
        }
    }
    // switches to main act
    private fun switchToMainActivity() {
        val mainContentLayout = findViewById<ConstraintLayout>(R.id.mainContentLayout)
        mainContentLayout.visibility = View.VISIBLE

        supportFragmentManager.popBackStack()

        isTaxFragmentDisplayed = false
        isSettingsFragmentDisplayed = false
        isHistoryFragmentDisplayed = false
    }
    // clears previous fragments
    private fun clearPreviousFragment() {
        val fragmentManager = supportFragmentManager
        val previousFragment = fragmentManager.findFragmentById(R.id.fragmentContainer)

        if (previousFragment != null) {
            val transaction = fragmentManager.beginTransaction()
            transaction.remove(previousFragment)
            transaction.commit()
        }
        if (isTaxFragmentDisplayed){
            switchToMainActivity()

            val navView =findViewById<NavigationView>(R.id.navigation_view)
            navView.menu.findItem(R.id.nav_Tax).isChecked =false
        }
        if (isSettingsFragmentDisplayed){
            switchToMainActivity()

            val navView =findViewById<NavigationView>(R.id.navigation_view)
            navView.menu.findItem(R.id.nav_Settings).isChecked = false
        }
        if (isHistoryFragmentDisplayed){
            switchToMainActivity()

            val navView =findViewById<NavigationView>(R.id.navigation_view)
            navView.menu.findItem(R.id.nav_History).isChecked = false
        }
    }

    fun sendToHistFrag(calcList: ArrayList<String>): history {
        val frag = history()
        val bundle = Bundle()
        bundle.putStringArrayList("calcList", calcList)
        frag.arguments = bundle
        return frag
    }

    private fun getNightModePreference(): Boolean {
        val sharedPreferences = getSharedPreferences(
            "MyAppPreferences",
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean("night_mode_enabled", false)
    }

}

