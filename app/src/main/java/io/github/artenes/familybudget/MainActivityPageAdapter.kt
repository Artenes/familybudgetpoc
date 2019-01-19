package io.github.artenes.familybudget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainActivityPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val pages: MutableList<FragmentTab> = mutableListOf()

    init {

        pages.add(FragmentTab("Bradesco", BankAccountFragment()))
        pages.add(FragmentTab("Cartao Bradesco", Fragment()))
        pages.add(FragmentTab("Cartao Nubank", Fragment()))
        pages.add(FragmentTab("Nuconta", Fragment()))
        pages.add(FragmentTab("Contas", Fragment()))

    }

    override fun getItem(position: Int): Fragment {
        return pages[position].fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].title
    }

}