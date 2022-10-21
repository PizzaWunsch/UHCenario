package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class BlockBreakScenario extends Scenario implements Listener {

    // instance variables
    private Material[] blocks;

    /**
     * Creates a new cut clean scenario and add this to local storage
     */
    public BlockBreakScenario() {
        super("cutclean");
        blocks = new Material[] { Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GOLD_ORE };
    }

    /**
     * Smelts the breaked ore direktly
     *
     * @param event the corresponding block break event
     */
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        // the block that got placed
        Block block = event.getBlock();
        // the player who breaked the block
        Player player = event.getPlayer();
        // if paranoia scenario is enabled
        if (Scenario.getScenario("paranoia").isEnabled()) {
            // loops all materials of block list
            for (Material material : this.blocks) {
                // if breaked block is in material list
                if (event.getBlock().getType().equals(material)) {
                    // the location of player
                    Location location = player.getLocation();
                    // publish the coordinates
                    Bukkit.broadcastMessage(UHCenario.getInstance().getMessage("scenarios.paranoia.coordinates")
                            .replace("%player%", player.getName()).replace("%x%", "" + location.getBlockX())
                            .replace("%y%", "" + location.getBlockY()).replace("%z%", "" + location.getBlockZ()));
                }
            }
        }
        // breaking block
        this.dropBlock(player, block, event.getExpToDrop());
    }

    /**
     * Drops the block that should be dropped by the scenario breaking handling
     *
     * @param block the block that should be breaked
     */
    public void dropBlock(Player player, Block block, int expToDrop) {

        // the multiplicator when doubleores or tripleores are enabled
        int multiplicator = 1;
        // if double ores is enabled
        if (Scenario.getScenario("doubleores").isEnabled())
            multiplicator = 2;
        // if tripple ores is enabled
        if (Scenario.getScenario("trippleores").isEnabled())
            multiplicator = 3;

        // if this scenario is enabled
        if (this.isEnabled()) {
            // if player is in survival mode
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                // clearing drops of block and play a effect
                block.getWorld().playEffect(block.getLocation().clone().add(0.5, 0.5, 0.5), Effect.SMOKE, 5);
                switch (block.getType()) {
                    case DIAMOND_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                            // if scenario diamond less is not enabled
                            if (!Scenario.getScenario("diamondless").isEnabled()) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.DIAMOND).amount(multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(7);
                            }
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case EMERALD_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.EMERALD).amount(multiplicator).build());
                            block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                    .setExperience(9);
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case GLOWING_REDSTONE_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.REDSTONE).amount(4 * multiplicator).build());
                            block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                    .setExperience(4);
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case LAPIS_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)) {
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.INK_SACK, (byte) 4).amount(4 * multiplicator).build());
                            block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                    .setExperience(4);
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case GOLD_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                            // if scenario gold less is not enabled
                            if (!Scenario.getScenario("goldless").isEnabled()) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.GOLD_INGOT).amount(multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(3);
                            }
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case IRON_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.IRON_INGOT).amount(multiplicator).build());
                            block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                    .setExperience(3);
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case COAL_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.WOOD_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.COAL).amount(multiplicator).build());
                            block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                    .setExperience(2);
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case QUARTZ_ORE:
                        if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.WOOD_PICKAXE)
                                || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.QUARTZ).amount(multiplicator).build());
                            block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                    .setExperience(5);
                        }
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    case STONE:
                        // drops the item
                        block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                new ItemBuilder(Material.COBBLESTONE).build());
                        // set the block to air
                        block.setType(Material.AIR);
                        break;
                    default:
                        break;
                }
            }
        } else if (Scenario.getScenario("doubleores").isEnabled()
                || Scenario.getScenario("trippleores").isEnabled()) { // if scenario is not enabled
            // if player is in survival mode
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                // if goldless or diamond less is enabled an breaked block is this ore
                if (!(Scenario.getScenario("goldless").isEnabled() && block.getType().equals(Material.GOLD_ORE))
                        && !(Scenario.getScenario("diamondless").isEnabled()
                        && block.getType().equals(Material.DIAMOND_ORE))) {
                    // clearing drops of block and play a effect
                    block.getWorld().playEffect(block.getLocation().clone().add(0.5, 0.5, 0.5), Effect.SMOKE, 5);
                    switch (block.getType()) {
                        case DIAMOND_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                                // if scenario diamond less is not enabled
                                if (!Scenario.getScenario("diamondless").isEnabled()) {
                                    // drops the item
                                    block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                            new ItemBuilder(Material.DIAMOND).amount(multiplicator).build());
                                    block.getWorld()
                                            .spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                            .setExperience(7);
                                }
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case EMERALD_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.EMERALD).amount(multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(9);
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case GLOWING_REDSTONE_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.REDSTONE).amount(4 * multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(4);
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case LAPIS_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.INK_SACK, (byte) 4).amount(4 * multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(4);
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case GOLD_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) {
                                // if scenario gold less is not enabled
                                if (!Scenario.getScenario("goldless").isEnabled()) {
                                    // drops the item
                                    block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                            new ItemBuilder(Material.GOLD_ORE).amount(multiplicator).build());
                                    block.getWorld()
                                            .spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                            .setExperience(3);
                                }
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case IRON_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.IRON_ORE).amount(multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(3);
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case COAL_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.WOOD_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.COAL).amount(multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(2);
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case QUARTZ_ORE:
                            if (player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.IRON_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.WOOD_PICKAXE)
                                    || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                                // drops the item
                                block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                        new ItemBuilder(Material.QUARTZ).amount(multiplicator).build());
                                block.getWorld().spawn(block.getLocation().clone().add(0.5, 0.5, 0.5), ExperienceOrb.class)
                                        .setExperience(5);
                            }
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        case STONE:
                            // drops the item
                            block.getWorld().dropItem(block.getLocation().clone().add(0.5, 0.5, 0.5),
                                    new ItemBuilder(Material.COBBLESTONE).build());
                            // set the block to air
                            block.setType(Material.AIR);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Handles the cut clean event for entity deaths as their drops should directly
     * be burned.
     *
     * @param event the corresponding entity death event
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // only if cut clean is active
        if (this.isEnabled()) {
            // not applicable for players
            if (event.getEntity() instanceof Player) {
                return;
            }
            // the cutclean drops handling for the different types of animals
            if (event.getEntity() instanceof Cow) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.COOKED_BEEF, 3));
                event.getDrops().add(new ItemStack(Material.LEATHER, 1));
            } else if (event.getEntity() instanceof Sheep) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.COOKED_MUTTON, 2));
                event.getDrops().add(new ItemStack(Material.WOOL, 1));
            } else if (event.getEntity() instanceof Rabbit) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.COOKED_RABBIT, 3));
            } else if (event.getEntity() instanceof Pig) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.GRILLED_PORK, 3));
            } else if (event.getEntity() instanceof Chicken) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 3));
                event.getDrops().add(new ItemStack(Material.FEATHER, 2));
            } else if (event.getEntity() instanceof Villager) {
                if (new Random().nextInt(99) < 50) {
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.BOOK, 1));
                }
            } else if (event.getEntity() instanceof Horse) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.LEATHER, 2));
            } else if (event.getEntity() instanceof PigZombie) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, 1));
                event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, 1));
            } else if (event.getEntity() instanceof Spider || event.getEntity() instanceof CaveSpider) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.STRING, 2));
            } else if (event.getEntity() instanceof Zombie) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, 2));
            } else if (event.getEntity() instanceof Skeleton) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.ARROW, 2));
                event.getDrops().add(new ItemStack(Material.BONE, 1));
            } else if (event.getEntity() instanceof Creeper) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.SULPHUR, 2));
            }
        }
    }

}
