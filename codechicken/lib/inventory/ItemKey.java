package codechicken.lib.inventory;

import static codechicken.lib.inventory.InventoryUtils.actualDamage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.base.Objects;

/**
 * Comparable ItemStack with a hashCode implementation.
 */
public class ItemKey implements Comparable<ItemKey>
{
    public ItemStack item;
    private int hashcode = 0;
    
    public ItemKey(ItemStack k)
    {
        item = k;
    }

    public ItemKey(Item item, int damage)
    {
        this(new ItemStack(item, 1, damage));
    }
    
    public ItemKey(Item item, int damage, NBTTagCompound compound)
    {
        this(item, damage);
        this.item.setTagCompound(compound);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ItemKey))
            return false;
        
        ItemKey k = (ItemKey)obj;
        return item.getUnlocalizedName() == k.item.getUnlocalizedName() &&
                actualDamage(item) == actualDamage(k.item) &&
                Objects.equal(item.stackTagCompound, k.item.stackTagCompound);
    }
    
    @Override
    public int hashCode()
    {
        return hashcode != 0 ? hashcode : (hashcode = Objects.hashCode(item.getUnlocalizedName(), actualDamage(item), item.stackTagCompound));
    }
    
    public int compareInt(int a, int b)
    {
        return a == b ? 0 : a < b ? -1 : 1;
    }

    @Override
    public int compareTo(ItemKey o)
    {
        if(item.getUnlocalizedName() != o.item.getUnlocalizedName())
            return item.getUnlocalizedName().compareTo(o.item.getUnlocalizedName());
        if(actualDamage(item) != actualDamage(o.item))
            return compareInt(actualDamage(item), actualDamage(o.item));
        return 0;
    }
}
