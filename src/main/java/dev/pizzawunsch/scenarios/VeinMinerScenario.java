package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class VeinMinerScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public VeinMinerScenario() {
        super("veinminer");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }
        if (!this.isEnabled())
            return;
        mineVein(event.getBlock(), new HashSet<>());
    }

    private void mineVein(Block block, Set<Block> minedBlocks) {
        if (minedBlocks.contains(block)) return;
        minedBlocks.add(block);
        if (isOre(block.getType())) {
            block.breakNaturally();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block adjacentBlock = block.getRelative(x, y, z);

                        if (adjacentBlock.getType() == block.getType()) {
                            mineVein(adjacentBlock, minedBlocks);
                        }
                    }
                }
            }
        }

    }

    private boolean isOre(Material material) {
        switch (material) {
            case COAL_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case REDSTONE_ORE:
            case LAPIS_ORE:
                return true;
            default:
                return false;
        }
    }
}



