namespace Phase03;
public class Student
{
    public int StudentNumber { get; set; }
    public string FirstName { get; set; }
    public string LastName { get; set; } 
    public double Average { get; set; }

    public override string ToString()
    { return $"FirstName: {FirstName}, LastName: {LastName}, ScoresAverage: {Average}"; }
}
