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
}
