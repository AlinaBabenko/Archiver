package Archiver;

/**
 * Created by Alina on 14.04.2017.
 */
public class FileProperties {
    private String name;
    private long size;
    private long compressedSize;
    private int compresiionMethod;

    public FileProperties(String name, long size, long compressedSize, int compresiionMethod) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compresiionMethod = compresiionMethod;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    // Calculate the compression ratio
    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressionMethod() {
        return compresiionMethod;
    }

    public long getCompressionRatio() {
        return 100 - ((compressedSize * 100) / size);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if(size>0) {
            builder.append(": size(");
            builder.append(size/1024);
            builder.append("Kb), compressed size(");
            builder.append(compressedSize/1024);
            builder.append("Kb), compression ratio");
            builder.append(getCompressionRatio());
            builder.append("%)");
        }
        return builder.toString();
    }
}
