package za.co.kernelpanic.edible.data.mapper

/**
 * We map from remote source to cache source using this interface
 */
interface Mapper<R, C> {

    fun mapToCache(remote: R):C
    fun mapToRemote(cache: C): R
}