package com.example.hyunndymovieapp.comment

import android.os.Parcel
import android.os.Parcelable
import org.w3c.dom.Comment

/* ----------------------------------------------------------------------------------------------
작성일: 20.03.05
작성자: 유현지
클래스명: CommentDTO
기능: 한줄평을 채울 DTO
---------------------------------------------------------------------------------------------- */

class CommentDTO : Parcelable{

    var userId : String ? = null // 유저 아이디
    var registeredTime : Long? = 0// 등록 시간
    var userProfile : Int? = 0 // 유저 프로필
    var comment : String? = null // 코멘트
    var ratingFigure : Float? = 0.0F// 평점
    var recommendedcount : Long? = 0

    companion object{
        @JvmField

        val CREATOR:Parcelable.Creator<CommentDTO> = object : Parcelable.Creator<CommentDTO> {
            override fun createFromParcel(source: Parcel?): CommentDTO {

                var comment = CommentDTO()

                comment.userId = source?.readString()
                comment.registeredTime = source?.readLong()
                comment.userProfile = source?.readInt()
                comment.comment = source?.readString()
                comment.ratingFigure = source?.readFloat()
                comment.recommendedcount = source?.readLong()

                return comment
            }

            override fun newArray(size: Int): Array<CommentDTO?> {
                return arrayOfNulls<CommentDTO>(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(userId)
        dest?.writeLong(registeredTime!!)
        dest?.writeInt(userProfile!!)
        dest?.writeString(comment)
        dest?.writeFloat(ratingFigure!!)
        dest?.writeLong(recommendedcount!!)
    }

    override fun describeContents(): Int {
        return 0
    }
}