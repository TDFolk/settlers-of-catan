package decoder.JsonModels;

/**
 * Created by bvanc on 10/3/2016.
 */
public class JsonLog {

    private JsonLine lines[];

    public JsonLog()
    {
        lines = new JsonLine[0];
    }

    public JsonLine[] getLines() {
        return lines;
    }

    public void setLines(JsonLine[] lines) {
        this.lines = lines;
    }

    public void addLine(String message, String source)
    {
        int lastIndex = lines.length;
        JsonLine[] newlines = new JsonLine[lastIndex + 1];

        for(int i = 0; i < lines.length; i++)
        {
            newlines[i] = lines[i];
        }

        newlines[lastIndex] = new JsonLine(message, source);

        lines = newlines;

    }
}
