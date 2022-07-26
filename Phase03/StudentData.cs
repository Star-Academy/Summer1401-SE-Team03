public class StudentData
{
    public int StudentNumber { get; set; }
    public string FirstName { get; set; }
    public string LastName { get; set; }
    public double Average { get; set; }

    public override string ToString()
    { return $"FistName= {FirstName}, LastName= {LastName}, ScoresAverage= {Average}"; }
}
