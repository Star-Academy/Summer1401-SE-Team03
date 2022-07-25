
using Newtonsoft.Json;
using System.Linq;
class Program
{
    static void Main()
    {
        using (StreamReader r = new StreamReader("data/students.json"))
        {
            string json = r.ReadToEnd();
            List<Student> students = JsonConvert.DeserializeObject<List<Student>>(json);
        }

        using (StreamReader r = new StreamReader("data/grades.json"))
        {
            string json = r.ReadToEnd();
            List<Grade> grades = JsonConvert.DeserializeObject<List<Grade>>(json);
            int i = 0;
            var sortedScores = grades.OrderByDescending(g => g.Score);
            foreach (var rs in result)
            {
                i = i + 1;
                Console.WriteLine($"The sorted score {i} is: {rs.Score} ");
            }

        }
    }
}
public class Student
{
    public int StudentNumber { get; set; }
    public string FirstName { get; set; }
    public string Lastname { get; set; }
}
public class Grade
{
    public int StudentNumber { get; set; }
    public string Lesson { get; set; }
    public string Score { get; set; }
}