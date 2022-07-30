using Newtonsoft.Json;
namespace Phase03;
public class ProgramController
{
    private static List<StudentData> students;
    private static List<StudentGrade> grades;
    public void Run()
    {
        InitializeLists();
        FindAverageScores();
        PrintResult(GetSortedAverages());
    }
    
    private static void InitializeLists()
    {
        students = GetDataFromJson<StudentData>("Resources/students.json");
        grades = GetDataFromJson<StudentGrade>("Resources/grades.json");
    }
    private static List<T> GetDataFromJson<T>(string filePath)
    {
        using (StreamReader fileReader = new StreamReader(filePath))
        {
            var json = fileReader.ReadToEnd();
            return JsonConvert.DeserializeObject<List<T>>(json);
        }
    }
    private static void FindAverageScores()
    {
        students.ForEach(student => student.Average = grades.Where(
            o => o.StudentNumber == student.StudentNumber).Average(g => g.Score));
    }
    private static List<StudentData> GetSortedAverages()
    {
        return students.OrderByDescending(o => o.Average).Take(3).ToList();
    }
    private static void PrintResult(List<StudentData> sortedStudents)
    {
        sortedStudents.ForEach(a => Console.WriteLine(a.ToString()));
    }
}