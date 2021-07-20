package com.example.eatoo.src.suggestion

import com.example.eatoo.src.suggestion.model.SuggestionMateResponse


interface MySuggestionView {

    fun onGetMateSuccess(response : SuggestionMateResponse)

    fun onGetMateFail(message : String)
}