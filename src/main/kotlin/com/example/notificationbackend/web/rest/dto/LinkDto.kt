package com.example.notificationbackend.web.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty

class OpenAiRequest(
    val model: String,
    val messages: List<MessagesDto>,
    val stream : Boolean? = null
)

class MessagesDto(
    val role: String,
    val content: String
)

class OpenAiReturnDto(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("object")
    val objectAi: String,
    @JsonProperty("created")
    val created: Int,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("system_fingerprint")
    val systemFingerprint: String,
    @JsonProperty("choices")
    val choices: List<ChoicesDto>,
    @JsonProperty("usage")
    val usage: UsageDto)
class UsageDto(
    @JsonProperty("prompt_tokens")
    val promptTokens: Int,
    @JsonProperty("completion_tokens")
    val completionTokens: Int,
    @JsonProperty("total_tokens")
    val totalTokens: Int
)
class ChoicesDto(
    @JsonProperty("index")
    val index: Int,
    @JsonProperty("message")
    val message: MessageDto,
    @JsonProperty("logprobs")
    val logprobs: String?,
    @JsonProperty("finish_reason")
    val finishReason: String
)
class MessageDto(
    @JsonProperty("role")
    val role: String,
    @JsonProperty("content")
    val content:String
)

class ChatCompletionChunkResponse(
    val response : List<ChatCompletionChunk>
)
class ChatCompletionChunk(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("choices")
    val choices: List<ChoiceDto>,
    @JsonProperty("created")
    val created: Int,
    @JsonProperty("model")
    val model: String,
    @JsonProperty("object")
    val objectAi: String,
    @JsonProperty("system_fingerprint")
    val systemFingerprint: String,
)

class ChoiceDto (
    @JsonProperty("delta")
    val delta : DeltaDto,
    @JsonProperty("finish_reason")
    val finishReason: String,
    @JsonProperty("index")
    val index: Int,
    @JsonProperty("logprobs")
    val logprobs: String
)

class DeltaDto(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("function_call")
    val functionCall: String,
    @JsonProperty("role")
    val role: String,
    @JsonProperty("tool_calls")
    val toolCalls: String
)