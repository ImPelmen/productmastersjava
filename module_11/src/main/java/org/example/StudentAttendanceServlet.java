package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.GroupDto;
import org.example.model.StudentAttendanceDto;
import org.example.util.AttendanceNameUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/attendance")
public class StudentAttendanceServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = 0L;
        try {
            groupId = Long.parseLong(req.getParameter("groupId"));
        } catch (NumberFormatException ignored) {
        }
        List<StudentAttendanceDto> list = getStudentsFromDB(groupId);
        List<GroupDto> groupList = getGroups();
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<style>  table {\n" +
                "            width: 50%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin: 20px 0;\n" +
                "            font-size: 18px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            border: 1px solid black;\n" +
                "            padding: 8px;\n" +
                "        }\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }");
        out.println("</style>");
        out.println("<body>");
        out.println("<form id='filter' action='/module_11/attendance' method='GET'>");
        out.println("Фильтрация по группам: <select name='groupId' id='filterSelect'>");
        out.println("<option value='0' " + (groupId == 0 ? "selected" : "") + ">Все</option>");
        for (GroupDto group:
                groupList) {
            out.println("<option value='" + group.getId() + "' "  + (group.getId().equals(groupId) ? "selected" : "") +
                    ">" + group.getName() + "</option>");
        }
        out.println("<input type='submit' id='submitButton' value='Фильтровать' hidden>");
        out.println("</form>");
        out.println("<h2>Посещение лекций</h2>");

        out.println("<form action='/module_11/attendance' method='POST'>");
        out.println("ФИО: <input type='text' name='name' required><br>");
        out.println("Группа: <input type='text' name='groupName' required><br>");
        out.println("Посетил: <select name='isAttended'><option value='true'>Да</option><option value='false'>Нет</option></select><br>");
        out.println("<input type='submit' value='Добавить'>");
        out.println("</form>");

        out.println("<table>");
        out.println("    <tr>\n" +
                "            <th>ФИО</th>\n" +
                "            <th>Группа</th>\n" +
                "            <th>Посетил</th>\n" +
                "            <th>Действия</th>\n" +
                "        </tr>");
        if (list.isEmpty()) {
            out.println("</table>");
            out.println("<h1>Нет данных в таблице<h1>");
        }
        for (StudentAttendanceDto studentAttendanceDto : list) {
            out.println("   <tr>\n" +
                    "            <td>" + studentAttendanceDto.getName() + "</td>\n" +
                    "            <td>" + studentAttendanceDto.getGroupName() + "</td>\n" +
                    "            <td>" + AttendanceNameUtil.fromBooleanToString(studentAttendanceDto.isAttended()) + "</td>\n" +
                    "            <td><form action='/module_11/attendance' method='POST'>" +
                    "            <input type='text' name='studentId' value='" + studentAttendanceDto.getId() + "' hidden><br>" +
                    "            <input style='background-color: red; color: white;' type='submit' value='Удалить'>" +
                    "           </form></td>\n" +
                    "        </tr>");
        }
        out.println("</table>");
        out.println("<script>" +
                "let filterSelect = document.getElementById('filterSelect');\n" +
                "filterSelect.addEventListener('change', function() { " +
                "document.getElementById('submitButton').click();" +
                "});" +
                "</script>");
    }

    private List<StudentAttendanceDto> getStudentsFromDB(Long groupId) {
        return Objects.isNull(groupId) || groupId == 0 ? getStudents() : getStudentByGroupId(groupId);
    }

    private List<StudentAttendanceDto> getStudents() {
        String sql = "Select s.id, s.name, s.is_attended, g.name as group_name from students s " +
                "left join groups g on s.group_id = g.id";
        List<StudentAttendanceDto> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                StudentAttendanceDto dto = getStudentAttendanceDto(rs);
                result.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<StudentAttendanceDto> getStudentByGroupId(Long groupId) {
        String sql = "Select s.id, s.name, s.is_attended, g.name as group_name from students s " +
                "left join groups g on s.group_id = g.id " +
                "where g.id = ?";
        List<StudentAttendanceDto> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, groupId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StudentAttendanceDto dto = getStudentAttendanceDto(rs);
                result.add(dto);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private StudentAttendanceDto getStudentAttendanceDto(ResultSet rs) throws SQLException {
        return StudentAttendanceDto.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .groupName(rs.getString("group_name"))
                .isAttended(rs.getBoolean("is_attended"))
                .build();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Сделал так, чтобы обойтись без ajax и не подключать jQuery через Java.
        String studentIdStr = req.getParameter("studentId");
        if (studentIdStr != null) {
            Long studentId = Long.parseLong(studentIdStr);
            deleteStudent(studentId);
        } else {
            createStudent(req);
        }
        resp.sendRedirect("/module_11/attendance");
    }

    private void deleteStudent(Long studentId) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, studentId);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createStudent(HttpServletRequest req) {
        String sql = "INSERT INTO students (name, group_id, is_attended) VALUES (?, ?, ?)";
        String name = req.getParameter("name");
        String groupName = req.getParameter("groupName").trim();
        boolean isAttended = Boolean.parseBoolean(req.getParameter("isAttended"));
        Integer groupId = getGroupIdByName(groupName);
        if (Objects.isNull(groupId)) {
            groupId = createGroup(groupName);
        }
        if (Objects.isNull(groupId)) {
            throw new RuntimeException("Что-то пошло не так в создании группы");
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, groupId);
            pstmt.setBoolean(3, isAttended);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Integer getGroupIdByName(String groupName) {
        String sql = "select * from groups where name = ?";
        Integer groupId = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                groupId = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupId;
    }

    private Integer createGroup(String groupName) {
        String sql = "INSERT INTO groups (name) VALUES (?)";
        Integer groupId = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, groupName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        groupId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupId;
    }

    private List<GroupDto> getGroups() {
        String sql = "SELECT id, name FROM groups";
        List<GroupDto> groups = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GroupDto dto = GroupDto.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .build();
                groups.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }
}
