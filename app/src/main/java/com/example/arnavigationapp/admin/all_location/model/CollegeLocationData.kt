package com.example.arnavigationapp.admin.all_location.model

/**
 * Data classes for representing college campuses and their building blocks
 * These are used to initialize location data in the application
 */

data class Block(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

data class College(
    val name: String,
    val blocks: List<Block>
)

/**
 * Predefined location data for different college campuses
 */
object CollegeLocations {
    
    // MVGR College blocks (updated)
    private val mvgrBlocks = listOf(
        Block("Mechanical Block", 18.06035, 83.40407),
        Block("ECE Block", 18.0601, 83.40469),
        Block("CSE Block", 18.06094, 83.40532),
        Block("Data Engineering Block", 18.06189, 83.40395),
        Block("Civil Block", 18.06108, 83.40532)
    )

    // GVPCE College blocks (updated)
    private val gvpceBlocks = listOf(
        Block("Civil Block", 17.8203, 83.3428),
        Block("Chemical Block", 17.8205, 83.3425),
        Block("Canteen", 17.8209, 83.3418),
        Block("EEE Block", 17.8213, 83.3415),
        Block("ECE Block", 17.8215, 83.3412),
        Block("CSE Block", 17.8213, 83.3411),
        Block("Admin Block", 17.8215, 83.3413),
        Block("IT Block", 17.8211, 83.3415),
        Block("Mechanical Block", 17.8207, 83.3426)
    )

    // List of all colleges (updated)
    val colleges = listOf(
        College("MVGR College", mvgrBlocks),
        College("GVPCE College", gvpceBlocks)
    )
    
    /**
     * Convert a Block to LocationData object
     * @param block The Block to convert
     * @param collegePrefix Optional prefix to add to the name (e.g., college name)
     * @return LocationData object with appropriate fields set
     */
    fun blockToLocationData(block: Block, collegePrefix: String = ""): LocationData {
        val uniqueId = System.currentTimeMillis() + block.name.hashCode()
        val name = if (collegePrefix.isEmpty()) block.name else "$collegePrefix - ${block.name}"
        val location = "${block.latitude},${block.longitude}"
        
        return LocationData(
            id = uniqueId,
            name = name,
            location = location,
            azimuth = "0.0",
            description = "${block.name} at ${block.latitude}, ${block.longitude}",
            imgUrl = "" // Default empty image URL
        )
    }
    
    /**
     * Get all blocks from all colleges as LocationData objects
     * @param useCollegePrefix Whether to prefix block names with college names
     * @return List of LocationData objects
     */
    fun getAllLocationsData(useCollegePrefix: Boolean = true): List<LocationData> {
        val result = mutableListOf<LocationData>()
        
        for (college in colleges) {
            for (block in college.blocks) {
                val prefix = if (useCollegePrefix) college.name else ""
                result.add(blockToLocationData(block, prefix))
            }
        }
        
        return result
    }
    
    /**
     * Get locations for a specific college
     * @param collegeName The name of the college
     * @param useCollegePrefix Whether to prefix block names with college name
     * @return List of LocationData objects for the specified college
     */
    fun getCollegeLocations(collegeName: String, useCollegePrefix: Boolean = true): List<LocationData> {
        val college = colleges.find { it.name == collegeName } ?: return emptyList()
        
        return college.blocks.map { block ->
            val prefix = if (useCollegePrefix) college.name else ""
            blockToLocationData(block, prefix)
        }
    }
}