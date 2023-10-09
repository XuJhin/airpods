package com.xujhin.lib.entity

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import java.io.Serializable

class BaseResp(
    var res: Any? = null,
    var createAt: Long = System.currentTimeMillis(),
    var updateAt: Long = System.currentTimeMillis(),
    var id:String=""
) : Serializable {


    constructor(updateResult: UpdateResult) : this() {
        if (updateResult.modifiedCount > 0) {
            this.res = "success"
        } else {
            this.res = "failed"
        }
    }


    constructor(deleteResult: DeleteResult) : this() {
        if (deleteResult.deletedCount > 0) {
            this.res = "success"
        } else {
            this.res = "failed"
        }
    }
}