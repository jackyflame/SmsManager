package com.jf.smsmanger.vm.main

import com.jf.smsmanger.base.vm.BaseVM
import com.jf.smsmanger.db.UserPresent
import com.jf.smsmanger.ui.main.UserFragment

class UserVM(fragment:UserFragment) : BaseVM<UserPresent>(UserPresent()){
}