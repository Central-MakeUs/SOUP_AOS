package com.makeus.eatoo.src.suggestion

import com.makeus.eatoo.src.suggestion.model.SuggestionMateResponse


interface MySuggestionView {

    fun onGetMateSuccess(response : SuggestionMateResponse)

    fun onGetMateFail(message : String)
}