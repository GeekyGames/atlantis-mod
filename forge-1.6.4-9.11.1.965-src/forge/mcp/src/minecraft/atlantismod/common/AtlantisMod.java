package atlantismod.common;

import api.player.client.ClientPlayerAPI;
import api.player.server.ServerPlayerAPI;
import atlantismod.common.entity.EntityClam;
import atlantismod.common.entity.EntityMermaid;
import atlantismod.common.entity.EntityShark;
import atlantismod.common.entity.EntityEel;
import atlantismod.common.entity.EntityElectricEel;
import atlantismod.common.entity.EntityKraken;
import atlantismod.common.entity.EntityAnglerFish;
import atlantismod.common.entity.EntityAtlantisFish;
import atlantismod.common.entity.EntityGiantSquid;
import atlantismod.common.entity.EntitySharkman;
import atlantismod.common.entity.EntitySquidman;
import atlantismod.common.entity.EntityWhale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.block.BlockSand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * 
 * Atlantis Mod
 * 
 * @author Graeme22
 * @author Lepreckaun
 * 
 * */

@Mod(modid="AtlantisMod",name="Atlantis",version="1.1.1")
@NetworkMod(clientSideRequired=true)
public class AtlantisMod {

	@Instance(value="AtlantisMod")
	public static AtlantisMod instance;
	@SidedProxy(clientSide="atlantismod.common.client.ClientProxy",serverSide="atlantismod.common.CommonProxy")
    public static CommonProxy proxy;
	
	public static final int dimensionID = 22;
	
	private static EnumArmorMaterial DivingSuit = EnumHelper.addArmorMaterial("DIVINGARMOR",15,new int[]{2,2,0,0},0);
	private static EnumToolMaterial PearlTool = EnumHelper.addToolMaterial("PEARLTOOL",3,1111,7.0F,2.5F,20);
	private static EnumToolMaterial Trident = EnumHelper.addToolMaterial("TRIDENTTOOL",1,1000,1.0F,5.0F,8);
	
	public static CreativeTabs tabAtlantis;

	public static Block portalAtlantisBlock;

	public static Block deepSandBlock, blockRottenPlanks;

	public static Item pearl, atlantisWand, trident;
	
	public static Item fishHead;
	
	public static Item swordPearl, pickaxePearl, shovelPearl, hoePearl, axePearl;
	
	public static Block blockPearl;
	
	public static ItemArmor divingHelmet, scubaSuit, oxygenTank, flippers;
	
	public static Block blockCoralOrange, blockCoralGreen, blockCoralRed, blockCoralPurple, blockCoralYellow;
	
	private static int fishID = 2222;
	
	public AtlantisMod() {
		ClientPlayerAPI.register("AtlantisMod", AtlantisClientPlayerBase.class);
		ServerPlayerAPI.register("AtlantisMod", AtlantisServerPlayerBase.class);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		tabAtlantis = new CreativeTabs("tabAtlantis") {
			public ItemStack getIconItemStack() {
				return new ItemStack(AtlantisMod.pearl.itemID, 1, 0);
			}
		};
		
		portalAtlantisBlock = (BlockAtlantisPortal)(new BlockAtlantisPortal(2222)).setUnlocalizedName("portalAtlantisBlock").setTextureName("portal");
		
		deepSandBlock = (new BlockSand(2223)).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setUnlocalizedName("deepSand").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:deep_sand");
		blockRottenPlanks = (new BlockRottenPlanks(2224)).setHardness(1.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("rottenPlanks").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:rotten_planks");

		pearl = (new Item(2225)).setUnlocalizedName("pearl").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:pearl");
		atlantisWand = (ItemAtlantisWand)(new ItemAtlantisWand(2226)).setUnlocalizedName("atlantisWand").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:portal_wand");
		trident = (new ItemTrident(2238,AtlantisMod.Trident)).setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:trident").setUnlocalizedName("trident").setMaxStackSize(1);
		
		fishHead = (new Item(2236)).setUnlocalizedName("fishHead").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:fish_head").setUnlocalizedName("fishHead");
		
		blockPearl = (new BlockOreStorage(2237)).setHardness(3.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockPearl").setTextureName("atlantismod:pearl_block").setCreativeTab(AtlantisMod.tabAtlantis);
		
		divingHelmet = (ItemDivingArmor)(new ItemDivingArmor(2227,AtlantisMod.DivingSuit,0,0)).setCreativeTab(AtlantisMod.tabAtlantis).setMaxStackSize(1).setUnlocalizedName("divingHelmet").setTextureName("atlantismod:diving_helmet");
		scubaSuit = (ItemDivingArmor)(new ItemDivingArmor(2228,AtlantisMod.DivingSuit,0,1)).setCreativeTab(AtlantisMod.tabAtlantis).setMaxStackSize(1).setUnlocalizedName("scubaSuit").setTextureName("atlantismod:scuba_suit");
		oxygenTank = (ItemDivingArmor)(new ItemDivingArmor(2229,AtlantisMod.DivingSuit,0,2)).setMaxDamage(10000).setCreativeTab(AtlantisMod.tabAtlantis).setMaxStackSize(1).setUnlocalizedName("oxygenTank").setTextureName("atlantismod:oxygen_tank");
		flippers = (ItemDivingArmor)(new ItemDivingArmor(2230,AtlantisMod.DivingSuit,0,3)).setCreativeTab(AtlantisMod.tabAtlantis).setMaxStackSize(1).setUnlocalizedName("flippers").setTextureName("atlantismod:flippers");
		
		blockCoralOrange = (new BlockCoral(2231)).setHardness(0.1F).setResistance(0.1F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("blockCoralOrange").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:orange_coral");
		blockCoralGreen = (new BlockCoral(2232)).setHardness(0.1F).setResistance(0.1F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("blockCoralGreen").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:green_coral");
		blockCoralRed = (new BlockCoral(2233)).setHardness(0.1F).setResistance(0.1F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("blockCoralRed").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:red_coral");
		blockCoralPurple = (new BlockCoral(2234)).setHardness(0.1F).setResistance(0.1F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("blockCoralPurple").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:purple_coral");
		blockCoralYellow = (new BlockCoral(2235)).setHardness(0.1F).setResistance(0.1F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("blockCoralYellow").setCreativeTab(AtlantisMod.tabAtlantis).setTextureName("atlantismod:yellow_coral");
		
		axePearl = (new ItemAxe(2239,AtlantisMod.PearlTool)).setUnlocalizedName("axePearl").setTextureName("atlantismod:pearl_axe").setCreativeTab(AtlantisMod.tabAtlantis);
		pickaxePearl = (new ItemPickaxe(2240,AtlantisMod.PearlTool)).setUnlocalizedName("pickaxePearl").setTextureName("atlantismod:pearl_pickaxe").setCreativeTab(AtlantisMod.tabAtlantis);
		hoePearl = (new ItemHoe(2241,AtlantisMod.PearlTool)).setUnlocalizedName("hoePearl").setTextureName("atlantismod:pearl_hoe").setCreativeTab(AtlantisMod.tabAtlantis);
		shovelPearl = (new ItemSpade(2242,AtlantisMod.PearlTool)).setUnlocalizedName("shovelPearl").setTextureName("atlantismod:pearl_shovel").setCreativeTab(AtlantisMod.tabAtlantis);
		swordPearl = (new ItemSword(2243,AtlantisMod.PearlTool)).setUnlocalizedName("swordPearl").setTextureName("atlantismod:pearl_sword").setCreativeTab(AtlantisMod.tabAtlantis);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		GameRegistry.registerWorldGenerator(new WorldGenAtlantis());
		DimensionManager.registerProviderType(AtlantisMod.dimensionID, WorldProviderAtlantis.class, false);
		DimensionManager.registerDimension(AtlantisMod.dimensionID, AtlantisMod.dimensionID);
		
		EntityRegistry.registerModEntity(EntityAtlantisFish.class,"Fish",1,this,40,3,true);
		EntityRegistry.addSpawn(EntityAtlantisFish.class,10,3,5,EnumCreatureType.waterCreature,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Fish.name","Fish");
		
		EntityRegistry.registerModEntity(EntitySquidman.class,"Squidman",2,this,40,3,true);
		EntityRegistry.addSpawn(EntitySquidman.class,6,2,3,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Squidman.name","Squidman");
		
		EntityRegistry.registerModEntity(EntityGiantSquid.class,"Giant Squid",3,this,40,3,true);
		EntityRegistry.addSpawn(EntityGiantSquid.class,3,1,1,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Giant Squid.name","Giant Squid");
		
		EntityRegistry.registerModEntity(EntityAnglerFish.class,"Angler Fish",4,this,40,3,true);
		EntityRegistry.addSpawn(EntityAnglerFish.class,4,1,3,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Angler Fish.name","Angler Fish");
		
		EntityRegistry.registerModEntity(EntityWhale.class,"Whale",5,this,40,3,true);
		EntityRegistry.addSpawn(EntityWhale.class,3,1,2,EnumCreatureType.waterCreature,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Whale.name","Whale");
		
		EntityRegistry.registerModEntity(EntityKraken.class,"Kraken",6,this,40,3,true);
		EntityRegistry.addSpawn(EntityKraken.class,1,1,3,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Kraken.name","Kraken");
		
		EntityRegistry.registerModEntity(EntityEel.class,"Eel",7,this,40,3,true);
		EntityRegistry.addSpawn(EntityEel.class,7,1,5,EnumCreatureType.waterCreature,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Eel.name","Eel");

		EntityRegistry.registerModEntity(EntityElectricEel.class,"Electric Eel",8,this,40,3,true);
		EntityRegistry.addSpawn(EntityElectricEel.class,4,1,3,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Electric Eel.name","Electric Eel");
		
		EntityRegistry.registerModEntity(EntitySharkman.class,"Sharkman",9,this,40,3,true);
		EntityRegistry.addSpawn(EntitySharkman.class,6,2,3,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Sharkman.name","Sharkman");
		
		EntityRegistry.registerModEntity(EntityShark.class,"Shark",10,this,40,3,true);
		EntityRegistry.addSpawn(EntityShark.class,4,1,3,EnumCreatureType.monster,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Shark.name","Shark");
		
		EntityRegistry.registerModEntity(EntityMermaid.class,"Mermaid",11,this,40,3,true);
		EntityRegistry.addSpawn(EntityMermaid.class,0,2,10,EnumCreatureType.waterCreature,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Mermaid.name","Mermaid");
		
		EntityRegistry.registerModEntity(EntityClam.class,"Clam",12,this,40,3,true);
		EntityRegistry.addSpawn(EntityClam.class,8,1,1,EnumCreatureType.waterCreature,BiomeGenBase.ocean);
		LanguageRegistry.instance().addStringLocalization("entity.AtlantisMod.Clam.name","Clam");

		LanguageRegistry.addName(portalAtlantisBlock, "Atlantis Portal Block");
		GameRegistry.registerBlock(portalAtlantisBlock, "portalAtlantisBlock");

		LanguageRegistry.addName(pearl, "Pearl");
		GameRegistry.registerItem(pearl, "pearl");
		
		LanguageRegistry.addName(trident, "Trident");
		GameRegistry.registerItem(trident, "trident");
		
		LanguageRegistry.addName(fishHead, "Fish Head");
		GameRegistry.registerItem(fishHead, "fishHead");

		LanguageRegistry.addName(blockRottenPlanks, "Rotten Planks");
		MinecraftForge.setBlockHarvestLevel(blockRottenPlanks, "axe", 1);
		GameRegistry.registerBlock(blockRottenPlanks,"blockRottenPlanks");

		LanguageRegistry.addName(deepSandBlock,"Deep Sand");
		MinecraftForge.setBlockHarvestLevel(deepSandBlock, "shovel", 1);
		GameRegistry.registerBlock(deepSandBlock,"deepSandBlock");
		
		LanguageRegistry.addName(atlantisWand,"Atlantis Teleporter");
		GameRegistry.registerItem(atlantisWand,"atlantisWand");
		
		LanguageRegistry.addName(blockPearl, "Block of Pearl");
		MinecraftForge.setBlockHarvestLevel(blockPearl, "pickaxe", 2);
		GameRegistry.registerBlock(blockPearl, "blockPearl");

		LanguageRegistry.addName(blockCoralOrange,"Orange Coral");
		GameRegistry.registerBlock(blockCoralOrange,"blockCoralOrange");
		LanguageRegistry.addName(blockCoralGreen,"Green Coral");
		GameRegistry.registerBlock(blockCoralGreen,"blockCoralGreen");
		LanguageRegistry.addName(blockCoralRed,"Red Coral");
		GameRegistry.registerBlock(blockCoralRed,"blockCoralRed");
		LanguageRegistry.addName(blockCoralPurple,"Purple Coral");
		GameRegistry.registerBlock(blockCoralPurple,"blockCoralPurple");
		LanguageRegistry.addName(blockCoralYellow,"Yellow Coral");
		GameRegistry.registerBlock(blockCoralYellow,"blockCoralYellow");
		
		LanguageRegistry.addName(axePearl, "Pearl Axe");
		GameRegistry.registerItem(axePearl, "axePearl");
		LanguageRegistry.addName(pickaxePearl, "Pearl Pickaxe");
		GameRegistry.registerItem(pickaxePearl, "pickaxePearl");
		LanguageRegistry.addName(hoePearl, "Pearl Hoe");
		GameRegistry.registerItem(hoePearl, "hoePearl");
		LanguageRegistry.addName(shovelPearl, "Pearl Shovel");
		GameRegistry.registerItem(shovelPearl, "shovelPearl");
		LanguageRegistry.addName(swordPearl, "Pearl Sword");
		GameRegistry.registerItem(swordPearl, "swordPearl");
		
		LanguageRegistry.addName(divingHelmet, "Diving Helmet");
		GameRegistry.registerItem(divingHelmet, "divingHelmet");
		LanguageRegistry.addName(scubaSuit, "Diving Suit");
		GameRegistry.registerItem(scubaSuit, "scubaSuit");
		LanguageRegistry.addName(oxygenTank, "Oxygen Tank");
		GameRegistry.registerItem(oxygenTank, "oxygenTank");
		LanguageRegistry.addName(flippers, "Flippers");
		GameRegistry.registerItem(flippers, "flippers");
	
		GameRegistry.addRecipe(new ItemStack(AtlantisMod.atlantisWand)," xx"," sx","s  ",'x',Item.diamond,'s',Item.stick);
        GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 2, 15),"x",'x',AtlantisMod.fishHead);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.blockPearl),"xxx","xxx","xxx",'x',AtlantisMod.pearl);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.pearl),"x",'x',AtlantisMod.blockPearl);

        GameRegistry.addRecipe(new ItemStack(AtlantisMod.axePearl),"xx ","xs "," s ",'x',AtlantisMod.pearl,'s',Item.stick);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.pickaxePearl),"xxx"," s "," s ",'x',AtlantisMod.pearl,'s',Item.stick);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.hoePearl),"xx "," s "," s ",'x',AtlantisMod.pearl,'s',Item.stick);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.swordPearl)," x "," x "," s ",'x',AtlantisMod.pearl,'s',Item.stick);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.shovelPearl)," x "," s "," s ",'x',AtlantisMod.pearl,'s',Item.stick);

        GameRegistry.addRecipe(new ItemStack(AtlantisMod.divingHelmet),"iii","x x",'i',Item.ingotIron,'x',Item.leather);
        GameRegistry.addRecipe(new ItemStack(AtlantisMod.scubaSuit),"x x","xsx","xxx",'x',Item.leather,'s',Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(AtlantisMod.oxygenTank),"xxx","xbx","xxx",'x',Item.ingotIron,'b',Item.glassBottle);
		GameRegistry.addRecipe(new ItemStack(AtlantisMod.flippers),"x x","x x",'x',Block.waterlily);

		LanguageRegistry.instance().addStringLocalization("itemGroup.tabAtlantis","en_US","Atlantis Mod");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
	
}
