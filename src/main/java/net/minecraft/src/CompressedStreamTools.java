package net.minecraft.src;

import com.jcraft.jzlib.GZIPInputStream;
import com.jcraft.jzlib.GZIPOutputStream;

import java.io.*;

public class CompressedStreamTools {
    /**
     * Load the gzipped compound from the inputstream.
     */
    public static NBTTagCompound readCompressed(InputStream par0InputStream) throws IOException {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));
        NBTTagCompound var2;

        try {
            var2 = read(var1);
        } finally {
            var1.close();
        }

        return var2;
    }

    /**
     * Write the compound, gzipped, to the outputstream.
     */
    public static void writeCompressed(NBTTagCompound par0NBTTagCompound, OutputStream par1OutputStream) throws IOException {
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));

        try {
            write(par0NBTTagCompound, var2);
        } finally {
            var2.close();
        }
    }

    public static NBTTagCompound decompress(byte[] par0ArrayOfByte) throws IOException {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(par0ArrayOfByte))));
        NBTTagCompound var2;

        try {
            var2 = read(var1);
        } finally {
            var1.close();
        }

        return var2;
    }

    public static byte[] compress(NBTTagCompound par0NBTTagCompound) throws IOException {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

        try {
            write(par0NBTTagCompound, var2);
        } finally {
            var2.close();
        }

        return var1.toByteArray();
    }

    public static NBTTagCompound readUncompressed(byte[] par0ArrayOfByte) throws IOException {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(par0ArrayOfByte)));
        NBTTagCompound var2;

        try {
            var2 = read(var1);
        } finally {
            var1.close();
        }

        return var2;
    }

    public static byte[] writeUncompressed(NBTTagCompound par0NBTTagCompound) throws IOException {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(var1);

        try {
            write(par0NBTTagCompound, var2);
        } finally {
            var2.close();
        }

        return var1.toByteArray();
    }

    /**
     * Reads from a CompressedStream.
     */
    public static NBTTagCompound read(DataInput par0DataInput) throws IOException {
        NBTBase var1 = NBTBase.readNamedTag(par0DataInput);

        if (var1 instanceof NBTTagCompound) {
            return (NBTTagCompound) var1;
        } else {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void write(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException {
        NBTBase.writeNamedTag(par0NBTTagCompound, par1DataOutput);
    }
}