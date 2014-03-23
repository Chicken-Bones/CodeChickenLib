package codechicken.lib.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fluids.FluidStack;
import codechicken.lib.vec.BlockCoord;

public class NBTDataWrapper implements MCDataInput, MCDataOutput
{
    private NBTTagList readList;
    private int readTag = 0;
    private NBTTagList writeList;
    
    public NBTDataWrapper(NBTTagList input)
    {
        readList = input;
    }
    
    public NBTDataWrapper()
    {
        writeList = new NBTTagList();
    }
    
    public NBTTagList toTag()
    {
        return writeList;
    }
    
    @Override
    public NBTDataWrapper writeLong(long l)
    {
        writeList.appendTag(new NBTTagLong(l));
        return this;
    }

    @Override
    public NBTDataWrapper writeInt(int i)
    {
        writeList.appendTag(new NBTTagInt(i));
        return this;
    }

    @Override
    public NBTDataWrapper writeShort(int s)
    {
        writeList.appendTag(new NBTTagShort((short) s));
        return this;
    }

    @Override
    public NBTDataWrapper writeByte(int b)
    {
        writeList.appendTag(new NBTTagByte((byte) b));
        return this;
    }

    @Override
    public NBTDataWrapper writeDouble(double d)
    {
        writeList.appendTag(new NBTTagDouble(d));
        return this;
    }

    @Override
    public NBTDataWrapper writeFloat(float f)
    {
        writeList.appendTag(new NBTTagFloat(f));
        return this;
    }

    @Override
    public NBTDataWrapper writeBoolean(boolean b)
    {
        writeList.appendTag(new NBTTagByte((byte) (b ? 1 : 0)));
        return this;
    }

    @Override
    public NBTDataWrapper writeChar(char c)
    {
        writeList.appendTag(new NBTTagShort((short)c));
        return this;
    }

    @Override
    public NBTDataWrapper writeByteArray(byte[] array)
    {
        writeList.appendTag(new NBTTagByteArray(array));
        return this;
    }

    @Override
    public NBTDataWrapper writeString(String s)
    {
        writeList.appendTag(new NBTTagString(s));
        return this;
    }

    @Override
    public NBTDataWrapper writeCoord(int x, int y, int z)
    {
        writeInt(x);
        writeInt(y);
        writeInt(z);
        return this;
    }

    @Override
    public NBTDataWrapper writeCoord(BlockCoord coord)
    {
        writeCoord(coord.x, coord.y, coord.z);
        return this;
    }

    @Override
    public NBTDataWrapper writeNBTTagCompound(NBTTagCompound tag)
    {
        writeList.appendTag(tag);
        return this;
    }

    @Override
    public NBTDataWrapper writeItemStack(ItemStack stack)
    {
        writeList.appendTag(stack.writeToNBT(new NBTTagCompound()));
        return this;
    }

    @Override
    public NBTDataWrapper writeFluidStack(FluidStack liquid)
    {
        writeList.appendTag(liquid.writeToNBT(new NBTTagCompound()));
        return this;
    }

    @Override
    public long readLong()
    {
        return ((NBTTagLong)readTag()).func_150291_c();
    }

    @Override
    public int readInt()
    {
        return ((NBTTagInt)readTag()).func_150287_d();
    }

    @Override
    public short readShort()
    {
        return ((NBTTagShort)readTag()).func_150289_e();
    }

    @Override
    public int readUShort()
    {
        return ((NBTTagShort)readTag()).func_150289_e() & 0xFFFF;
    }

    @Override
    public byte readByte()
    {
        return ((NBTTagByte)readTag()).func_150290_f();
    }

    @Override
    public int readUByte()
    {
        return ((NBTTagByte)readTag()).func_150290_f() & 0xFF;
    }

    @Override
    public double readDouble()
    {
        return ((NBTTagDouble)readTag()).func_150290_f();
    }

    @Override
    public float readFloat()
    {
        return ((NBTTagFloat)readTag()).func_150288_h();
    }

    @Override
    public boolean readBoolean()
    {
        return ((NBTTagByte)readTag()).func_150290_f() != 0;
    }

    @Override
    public char readChar()
    {
        return (char)((NBTTagShort)readTag()).func_150289_e();
    }

    @Override
    public byte[] readByteArray(int length)
    {
        return ((NBTTagByteArray)readTag()).func_150292_c();
    }

    @Override
    public String readString()
    {
        return ((NBTTagString)readTag()).func_150285_a_();
    }

    @Override
    public BlockCoord readCoord()
    {
        return new BlockCoord(readInt(), readInt(), readInt());
    }

    @Override
    public NBTTagCompound readNBTTagCompound()
    {
        return (NBTTagCompound)readTag();
    }

    @Override
    public ItemStack readItemStack()
    {
        return ItemStack.loadItemStackFromNBT(readNBTTagCompound());
    }

    @Override
    public FluidStack readFluidStack()
    {
        return FluidStack.loadFluidStackFromNBT(readNBTTagCompound());
    }
    
    private NBTBase readTag()
    {
        //TODO fix this
        return readList.getCompoundTagAt(readTag++);
    }
}
