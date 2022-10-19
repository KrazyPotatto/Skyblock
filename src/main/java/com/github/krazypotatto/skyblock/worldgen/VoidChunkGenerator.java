package com.github.krazypotatto.skyblock.worldgen;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidChunkGenerator extends ChunkGenerator {

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random r, int chunkX, int chunkZ, @NotNull ChunkData chunkData){
        World world = Bukkit.getWorld(worldInfo.getUID());
        for(int y = chunkData.getMinHeight(); y < chunkData.getMaxHeight(); y++){
            for(int x = 0; x < 16; x ++){
                for(int z = 0; z < 16; z++){
                    chunkData.setBlock(x, y, z, Material.AIR);
                    world.setBiome(chunkX + x, y, chunkZ + z, Biome.PLAINS);
                }
            }
        }

    }

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random r, int chunkX, int chunkZ, @NotNull ChunkData chunkData){
        for(int y = chunkData.getMinHeight(); y < chunkData.getMaxHeight(); y++){
            for(int x = 0; x < 16; x ++){
                for(int z = 0; z < 16; z++){
                    chunkData.setBlock(x, y, z, Material.AIR);
                }
            }
        }
    }

    @Override
    public void generateCaves(@NotNull WorldInfo worldInfo, @NotNull Random r, int chunkX, int chunkZ, @NotNull ChunkData chunkData){
        for(int y = chunkData.getMinHeight(); y < chunkData.getMaxHeight(); y++){
            for(int x = 0; x < 16; x ++){
                for(int z = 0; z < 16; z++){
                    chunkData.setBlock(x, y, z, Material.AIR);
                }
            }
        }
    }

}
