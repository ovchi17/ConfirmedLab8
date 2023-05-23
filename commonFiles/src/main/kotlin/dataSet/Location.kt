package dataSet

import kotlinx.serialization.Serializable

/**
 * Location. Sub-model of: <code>Route</code>.
 *
 * @constructor x: Long?, y: Long, z: Int
 * @author OvchinnikovI17
 * @since 1.0.0
 */
@Serializable
class Location {

    constructor(checkX: Long?, checkY: Long?, checkZ: Int?){
        x = checkX
        y = checkY
        z = checkZ
    }

    var y: Long?
        set(value){
            field = value
        }

    var x: Long?

    var z: Int?
        set(value){
            field = value
        }
}