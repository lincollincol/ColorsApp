package linc.com.colorsapp.domain

import android.os.Parcel
import android.os.Parcelable
import linc.com.colorsapp.utils.readBoolean
import linc.com.colorsapp.utils.writeBoolean

data class ColorModel (
    var name: String? = null,
    var hex: String? = null,
    var rgb: String? = null,
    var saved: Boolean? = false,
    var custom: Boolean? = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readBoolean()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeStringArray(arrayOf(name, hex, rgb))
        dest?.writeBoolean(saved)
        dest?.writeBoolean(custom)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ColorModel> {
        override fun createFromParcel(parcel: Parcel): ColorModel {
            return ColorModel(parcel)
        }

        override fun newArray(size: Int): Array<ColorModel?> {
            return arrayOfNulls(size)
        }
    }
}