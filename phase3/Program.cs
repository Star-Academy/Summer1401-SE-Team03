
using Newtonsoft.Json; //nameSpace
class Program
{
    private static List<StudentData> students;
    private static List<StudentGrade> grades;
    static void Main()
    {
        HandlingInput();
        makeAverageScores();
    }
    private static void HandlingInput()//one input
    {
        using (StreamReader studentFileReader = new StreamReader("data/students.json"))
        using (StreamReader gradeFileReader = new StreamReader("data/grades.json"))
        {
            string studentJson = studentFileReader.ReadToEnd();
            students = JsonConvert.DeserializeObject<List<StudentData>>(studentJson);

            string gradeJson = gradeFileReader.ReadToEnd();
            grades = JsonConvert.DeserializeObject<List<StudentGrade>>(gradeJson);
        }
    }
    private static void makeAverageScores()
    {
        students.ForEach(student => student.Average = grades.Where(
            o => o.StudentNumber == student.StudentNumber).Average(g => g.Score));
        var sortedAverage = students.OrderByDescending(o => o.Average).Take(3).ToList();
        sortedAverage.ForEach(a => Console.WriteLine(a.ToString()));
    }
}
