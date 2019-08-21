package com.github.pedrodimoura.news.common.domain

import com.github.pedrodimoura.news.common.presentation.viewmodel.FlowState

interface DomainInteractor<Input, Output> {

    suspend fun execute(params: Input? = null): FlowState<Output>

}