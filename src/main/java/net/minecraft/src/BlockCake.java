package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglercraftRandom;

public class BlockCake extends Block {
    private Icon cakeTopIcon;
    private Icon cakeBottomIcon;
    private Icon field_94382_c;

    protected BlockCake(int par1) {
        super(par1, Material.cake);
        this.setTickRandomly(true);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float var6 = 0.0625F;
        float var7 = (float) (1 + var5 * 2) / 16.0F;
        float var8 = 0.5F;
        this.setBlockBounds(var7, 0.0F, var6, 1.0F - var6, var8, 1.0F - var6);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender() {
        float var1 = 0.0625F;
        float var2 = 0.5F;
        this.setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box
     * can change after the pool has been cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        float var6 = 0.0625F;
        float var7 = (float) (1 + var5 * 2) / 16.0F;
        float var8 = 0.5F;
        return AxisAlignedBB.getAABBPool().getAABB((float) par2 + var7, par3, (float) par4 + var6, (float) (par2 + 1) - var6, (float) par3 + var8 - var6,
                (float) (par4 + 1) - var6);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        float var6 = 0.0625F;
        float var7 = (float) (1 + var5 * 2) / 16.0F;
        float var8 = 0.5F;
        return AxisAlignedBB.getAABBPool().getAABB((float) par2 + var7, par3, (float) par4 + var6, (float) (par2 + 1) - var6, (float) par3 + var8, (float) (par4 + 1) - var6);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture.
     * Args: side, metadata
     */
    public Icon getIcon(int par1, int par2) {
        return par1 == 1 ? this.cakeTopIcon : (par1 == 0 ? this.cakeBottomIcon : (par2 > 0 && par1 == 4 ? this.field_94382_c : this.blockIcon));
    }

    /**
     * When this method is called, your block should register all the icons it needs
     * with the given IconRegister. This is the only chance you get to register
     * icons.
     */
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("cake_side");
        this.field_94382_c = par1IconRegister.registerIcon("cake_inner");
        this.cakeTopIcon = par1IconRegister.registerIcon("cake_top");
        this.cakeBottomIcon = par1IconRegister.registerIcon("cake_bottom");
    }

    /**
     * If this block doesn't render as an ordinary block it will return False
     * (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
        return true;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Heals the player and removes a slice from the cake.
     */
    private void eatCakeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        if (par5EntityPlayer.canEat(false)) {
            par5EntityPlayer.getFoodStats().addStats(2, 0.1F);
            int var6 = par1World.getBlockMetadata(par2, par3, par4) + 1;

            if (var6 >= 6) {
                par1World.setBlockToAir(par2, par3, par4);
            } else {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates.
     * Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
     * blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    /**
     * Can this block stay at this position. Similar to canPlaceBlockAt except gets
     * checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(EaglercraftRandom par1Random) {
        return 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, EaglercraftRandom par2Random, int par3) {
        return 0;
    }

    /**
     * only called by clickMiddleMouseButton , and passed to
     * inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return Item.cake.itemID;
    }
}
