package com.tunanh.clicktofood_hilt.data.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Food(
    @PrimaryKey
    val id: Long = 0,
    val title: String = "",
    val cost: Int = 0,
    var like:Boolean=false,
    val star: Double? = 0.0,
    val img: String? = "",
    var amount:Int=0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeInt(cost)
        parcel.writeByte(if (like) 1 else 0)
        parcel.writeValue(star)
        parcel.writeString(img)
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}
//    constructor(parcel: Parcel) : this(
//        parcel.readLong(),
//        parcel.readString().toString(),
//        parcel.readInt(),
//        parcel.readValue(Double::class.java.classLoader) as? Double,
//        parcel.readString(),
//        parcel.readInt()
//    )
//
//    override fun describeContents(): Int =0
//
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        dest.writeLong(id)
//        dest.writeString(title)
//        dest.writeInt(cost)
//        if (star != null) {
//            dest.writeDouble(star)
//        }
//        dest.writeString(img)
//        dest.writeInt(amount)
//    }
//
//    companion object CREATOR : Parcelable.Creator<Food> {
//        override fun createFromParcel(parcel: Parcel): Food {
//            return Food(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Food?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
