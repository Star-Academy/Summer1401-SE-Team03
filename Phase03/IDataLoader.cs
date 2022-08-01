namespace Phase03;

public interface IDataLoader
{
    public T Load<T>(string input);
}