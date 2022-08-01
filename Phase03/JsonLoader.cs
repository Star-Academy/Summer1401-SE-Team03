using Newtonsoft.Json;

namespace Phase03;

public class JsonLoader : IDataLoader
{
    public T Load<T>(string filePath)
    {
        using var fileReader = new StreamReader(filePath);
        var json = fileReader.ReadToEnd();
        return JsonConvert.DeserializeObject<T>(json);
    }
}