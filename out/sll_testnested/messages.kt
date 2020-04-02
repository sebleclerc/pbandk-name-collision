@file:UseSerializers(pbandk.ser.TimestampSerializer::class)

package sll_testnested

import kotlinx.serialization.*
import kotlinx.serialization.json.*

sealed class MessageAType(override val value: Int, override val name: String? = null) : pbandk.Message.Enum {
    override fun equals(other: kotlin.Any?) = other is MessageAType && other.value == value
    override fun hashCode() = value.hashCode()
    override fun toString() = "MessageAType.${name ?: "UNRECOGNIZED"}(value=$value)"

    object FULL : MessageAType(0, "Full")
    object OUTER : MessageAType(1, "Outer")
    object MIDDLE : MessageAType(2, "Middle")
    class UNRECOGNIZED(value: Int) : MessageAType(value)

    companion object : pbandk.Message.Enum.Companion<MessageAType> {
        val values: List<MessageAType> by lazy { listOf(FULL, OUTER, MIDDLE) }
        override fun fromValue(value: Int) = values.firstOrNull { it.value == value } ?: UNRECOGNIZED(value)
        override fun fromName(name: String) = values.firstOrNull { it.name == name } ?: throw IllegalArgumentException("No MessageAType with name: $name")
    }
}

data class MessageA(
    val id: String = "",
    val name: String = "",
    val type: sll_testnested.MessageAType = sll_testnested.MessageAType.fromValue(0),
    val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message<MessageA> {
    override operator fun plus(other: MessageA?) = protoMergeImpl(other)
    override val protoSize by lazy { protoSizeImpl() }
    override fun protoMarshal(m: pbandk.Marshaller) = protoMarshalImpl(m)
    override fun jsonMarshal(json: Json) = jsonMarshalImpl(json)
    fun toJsonMapper() = toJsonMapperImpl()
    companion object : pbandk.Message.Companion<MessageA> {
        val defaultInstance by lazy { MessageA() }
        override fun protoUnmarshal(u: pbandk.Unmarshaller) = MessageA.protoUnmarshalImpl(u)
        override fun jsonUnmarshal(json: Json, data: String) = MessageA.jsonUnmarshalImpl(json, data)
    }

    @Serializable
    data class JsonMapper (
        @SerialName("id")
        val id: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("type")
        val type: String? = null
    ) {
        fun toMessage() = toMessageImpl()
    }
}

data class MessageB(
    val id: String = "",
    val name: String = "",
    val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message<MessageB> {
    override operator fun plus(other: MessageB?) = protoMergeImpl(other)
    override val protoSize by lazy { protoSizeImpl() }
    override fun protoMarshal(m: pbandk.Marshaller) = protoMarshalImpl(m)
    override fun jsonMarshal(json: Json) = jsonMarshalImpl(json)
    fun toJsonMapper() = toJsonMapperImpl()
    companion object : pbandk.Message.Companion<MessageB> {
        val defaultInstance by lazy { MessageB() }
        override fun protoUnmarshal(u: pbandk.Unmarshaller) = MessageB.protoUnmarshalImpl(u)
        override fun jsonUnmarshal(json: Json, data: String) = MessageB.jsonUnmarshalImpl(json, data)
    }

    @Serializable
    data class JsonMapper (
        @SerialName("id")
        val id: String? = null,
        @SerialName("name")
        val name: String? = null
    ) {
        fun toMessage() = toMessageImpl()
    }

    sealed class MessageAType(override val value: Int, override val name: String? = null) : pbandk.Message.Enum {
        override fun equals(other: kotlin.Any?) = other is MessageAType && other.value == value
        override fun hashCode() = value.hashCode()
        override fun toString() = "MessageAType.${name ?: "UNRECOGNIZED"}(value=$value)"

        object SOME : MessageAType(0, "Some")
        object OTHER : MessageAType(1, "Other")
        object NESTED : MessageAType(2, "Nested")
        class UNRECOGNIZED(value: Int) : MessageAType(value)

        companion object : pbandk.Message.Enum.Companion<MessageAType> {
            val values: List<MessageAType> by lazy { listOf(SOME, OTHER, NESTED) }
            override fun fromValue(value: Int) = values.firstOrNull { it.value == value } ?: UNRECOGNIZED(value)
            override fun fromName(name: String) = values.firstOrNull { it.name == name } ?: throw IllegalArgumentException("No MessageAType with name: $name")
        }
    }

    data class MessageA(
        val isSame: Boolean = false,
        val value: Int = 0,
        val type: sll_testnested.MessageB.MessageAType = sll_testnested.MessageB.MessageAType.fromValue(0),
        val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message<MessageA> {
        override operator fun plus(other: MessageA?) = protoMergeImpl(other)
        override val protoSize by lazy { protoSizeImpl() }
        override fun protoMarshal(m: pbandk.Marshaller) = protoMarshalImpl(m)
        override fun jsonMarshal(json: Json) = jsonMarshalImpl(json)
        fun toJsonMapper() = toJsonMapperImpl()
        companion object : pbandk.Message.Companion<MessageA> {
            val defaultInstance by lazy { MessageA() }
            override fun protoUnmarshal(u: pbandk.Unmarshaller) = MessageA.protoUnmarshalImpl(u)
            override fun jsonUnmarshal(json: Json, data: String) = MessageA.jsonUnmarshalImpl(json, data)
        }

        @Serializable
        data class JsonMapper (
            @SerialName("isSame")
            val isSame: Boolean? = null,
            @SerialName("value")
            val value: Int? = null,
            @SerialName("type")
            val type: String? = null
        ) {
            fun toMessage() = toMessageImpl()
        }
    }
}

fun MessageA?.orDefault() = this ?: MessageA.defaultInstance

private fun MessageA.protoMergeImpl(plus: MessageA?): MessageA = plus?.copy(
    unknownFields = unknownFields + plus.unknownFields
) ?: this

private fun MessageA.protoSizeImpl(): Int {
    var protoSize = 0
    if (id.isNotEmpty()) protoSize += pbandk.Sizer.tagSize(1) + pbandk.Sizer.stringSize(id)
    if (name.isNotEmpty()) protoSize += pbandk.Sizer.tagSize(2) + pbandk.Sizer.stringSize(name)
    if (type.value != 0) protoSize += pbandk.Sizer.tagSize(3) + pbandk.Sizer.enumSize(type)
    protoSize += unknownFields.entries.sumBy { it.value.size() }
    return protoSize
}

private fun MessageA.protoMarshalImpl(protoMarshal: pbandk.Marshaller) {
    if (id.isNotEmpty()) protoMarshal.writeTag(10).writeString(id)
    if (name.isNotEmpty()) protoMarshal.writeTag(18).writeString(name)
    if (type.value != 0) protoMarshal.writeTag(24).writeEnum(type)
    if (unknownFields.isNotEmpty()) protoMarshal.writeUnknownFields(unknownFields)
}

private fun MessageA.Companion.protoUnmarshalImpl(protoUnmarshal: pbandk.Unmarshaller): MessageA {
    var id = ""
    var name = ""
    var type: sll_testnested.MessageAType = sll_testnested.MessageAType.fromValue(0)
    while (true) when (protoUnmarshal.readTag()) {
        0 -> return MessageA(id, name, type, protoUnmarshal.unknownFields())
        10 -> id = protoUnmarshal.readString()
        18 -> name = protoUnmarshal.readString()
        24 -> type = protoUnmarshal.readEnum(sll_testnested.MessageAType.Companion)
        else -> protoUnmarshal.unknownField()
    }
}

private fun MessageA.toJsonMapperImpl(): MessageA.JsonMapper =
    MessageA.JsonMapper(
        id.takeIf { it != "" },
        name.takeIf { it != "" },
        type?.name
    )

private fun MessageA.JsonMapper.toMessageImpl(): MessageA =
    MessageA(
        id = id ?: "",
        name = name ?: "",
        type = type?.let { sll_testnested.MessageAType.fromName(it) } ?: sll_testnested.MessageAType.fromValue(0)
    )

private fun MessageA.jsonMarshalImpl(json: Json): String =
    json.stringify(MessageA.JsonMapper.serializer(), toJsonMapper())

private fun MessageA.Companion.jsonUnmarshalImpl(json: Json, data: String): MessageA {
    val mapper = json.parse(MessageA.JsonMapper.serializer(), data)
    return mapper.toMessage()
}

fun MessageB?.orDefault() = this ?: MessageB.defaultInstance

private fun MessageB.protoMergeImpl(plus: MessageB?): MessageB = plus?.copy(
    unknownFields = unknownFields + plus.unknownFields
) ?: this

private fun MessageB.protoSizeImpl(): Int {
    var protoSize = 0
    if (id.isNotEmpty()) protoSize += pbandk.Sizer.tagSize(1) + pbandk.Sizer.stringSize(id)
    if (name.isNotEmpty()) protoSize += pbandk.Sizer.tagSize(2) + pbandk.Sizer.stringSize(name)
    protoSize += unknownFields.entries.sumBy { it.value.size() }
    return protoSize
}

private fun MessageB.protoMarshalImpl(protoMarshal: pbandk.Marshaller) {
    if (id.isNotEmpty()) protoMarshal.writeTag(10).writeString(id)
    if (name.isNotEmpty()) protoMarshal.writeTag(18).writeString(name)
    if (unknownFields.isNotEmpty()) protoMarshal.writeUnknownFields(unknownFields)
}

private fun MessageB.Companion.protoUnmarshalImpl(protoUnmarshal: pbandk.Unmarshaller): MessageB {
    var id = ""
    var name = ""
    while (true) when (protoUnmarshal.readTag()) {
        0 -> return MessageB(id, name, protoUnmarshal.unknownFields())
        10 -> id = protoUnmarshal.readString()
        18 -> name = protoUnmarshal.readString()
        else -> protoUnmarshal.unknownField()
    }
}

private fun MessageB.toJsonMapperImpl(): MessageB.JsonMapper =
    MessageB.JsonMapper(
        id.takeIf { it != "" },
        name.takeIf { it != "" }
    )

private fun MessageB.JsonMapper.toMessageImpl(): MessageB =
    MessageB(
        id = id ?: "",
        name = name ?: ""
    )

private fun MessageB.jsonMarshalImpl(json: Json): String =
    json.stringify(MessageB.JsonMapper.serializer(), toJsonMapper())

private fun MessageB.Companion.jsonUnmarshalImpl(json: Json, data: String): MessageB {
    val mapper = json.parse(MessageB.JsonMapper.serializer(), data)
    return mapper.toMessage()
}

fun MessageB.MessageA?.orDefault() = this ?: MessageB.MessageA.defaultInstance

private fun MessageB.MessageA.protoMergeImpl(plus: MessageB.MessageA?): MessageB.MessageA = plus?.copy(
    unknownFields = unknownFields + plus.unknownFields
) ?: this

private fun MessageB.MessageA.protoSizeImpl(): Int {
    var protoSize = 0
    if (isSame) protoSize += pbandk.Sizer.tagSize(1) + pbandk.Sizer.boolSize(isSame)
    if (value != 0) protoSize += pbandk.Sizer.tagSize(2) + pbandk.Sizer.int32Size(value)
    if (type.value != 0) protoSize += pbandk.Sizer.tagSize(3) + pbandk.Sizer.enumSize(type)
    protoSize += unknownFields.entries.sumBy { it.value.size() }
    return protoSize
}

private fun MessageB.MessageA.protoMarshalImpl(protoMarshal: pbandk.Marshaller) {
    if (isSame) protoMarshal.writeTag(8).writeBool(isSame)
    if (value != 0) protoMarshal.writeTag(16).writeInt32(value)
    if (type.value != 0) protoMarshal.writeTag(24).writeEnum(type)
    if (unknownFields.isNotEmpty()) protoMarshal.writeUnknownFields(unknownFields)
}

private fun MessageB.MessageA.Companion.protoUnmarshalImpl(protoUnmarshal: pbandk.Unmarshaller): MessageB.MessageA {
    var isSame = false
    var value = 0
    var type: sll_testnested.MessageB.MessageAType = sll_testnested.MessageB.MessageAType.fromValue(0)
    while (true) when (protoUnmarshal.readTag()) {
        0 -> return MessageB.MessageA(isSame, value, type, protoUnmarshal.unknownFields())
        8 -> isSame = protoUnmarshal.readBool()
        16 -> value = protoUnmarshal.readInt32()
        24 -> type = protoUnmarshal.readEnum(sll_testnested.MessageB.MessageAType.Companion)
        else -> protoUnmarshal.unknownField()
    }
}

private fun MessageB.MessageA.toJsonMapperImpl(): MessageB.MessageA.JsonMapper =
    MessageB.MessageA.JsonMapper(
        isSame,
        value,
        type?.name
    )

private fun MessageB.MessageA.JsonMapper.toMessageImpl(): MessageB.MessageA =
    MessageB.MessageA(
        isSame = isSame ?: false,
        value = value ?: 0,
        type = type?.let { sll_testnested.MessageB.MessageAType.fromName(it) } ?: sll_testnested.MessageB.MessageAType.fromValue(0)
    )

private fun MessageB.MessageA.jsonMarshalImpl(json: Json): String =
    json.stringify(MessageB.MessageA.JsonMapper.serializer(), toJsonMapper())

private fun MessageB.MessageA.Companion.jsonUnmarshalImpl(json: Json, data: String): MessageB.MessageA {
    val mapper = json.parse(MessageB.MessageA.JsonMapper.serializer(), data)
    return mapper.toMessage()
}
