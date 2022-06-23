package com.epitech.cashmanager.cashregister

import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.epitech.cashmanager.R
import com.epitech.cashmanager.article.Article
import com.epitech.cashmanager.databinding.FragmentCashRegisterBinding
import kotlin.random.Random


class CashRegisterFragment : Fragment() {

    private lateinit var binding : FragmentCashRegisterBinding
    private var tempItem : MutableList<Article> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash_register, container, false)

        binding.submitButton.setOnClickListener{ nextPage() }

        initShopping()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, NavHostFragment.findNavController(this))
                ||super.onOptionsItemSelected(item)
    }

    private fun nextPage(){
        NavHostFragment.findNavController(this).navigate(R.id.action_cashRegisterFragment_to_confirmationFragment)
    }

    private fun initShopping(){
        for (i in 1..30){
            tempItem.add(Article("article$i", Random.nextInt(1,100).toString(), Random.nextInt(1,999) ))
        }

        val listItems = binding.itemsList

        for(item in tempItem){

            var nameTextView = TextView(context, null, 0, R.style.items_style)
            var priceTextView = TextView(context, null, 0, R.style.items_style)
            var newButton = Button(context)
            var newRow = TableRow(context)

            nameTextView.text = item.name
            nameTextView.id = View.generateViewId()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                nameTextView.setTextAppearance(R.style.items_style)
            }

            priceTextView.text = item.price.toString()
            priceTextView.id = View.generateViewId()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                nameTextView.setTextAppearance(R.style.items_style)
            }

            newButton.id = View.generateViewId()
            newButton.text = getString(R.string.add_button_col)

            newRow.addView(nameTextView)
            newRow.addView(priceTextView)
            newRow.addView(newButton)

            listItems.addView(newRow)
        }

    }
}