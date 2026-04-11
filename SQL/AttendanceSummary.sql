CREATE TABLE attendance_summary (
                                    summaryID INT AUTO_INCREMENT PRIMARY KEY,
                                    undergraduateId VARCHAR(50) NOT NULL,
                                    courseCode VARCHAR(20) NOT NULL,

                                    totalSessions INT DEFAULT 0,
                                    presentCount INT DEFAULT 0,
                                    absentCount INT DEFAULT 0,
                                    lateCount INT DEFAULT 0,

                                    attendancePercentage DECIMAL(5,2) DEFAULT 0,

                                    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO attendance_summary (
    undergraduateId, courseCode,
    totalSessions, presentCount, absentCount, lateCount, attendancePercentage
)
SELECT
    undergraduateId,
    courseCode,
    COUNT(*) AS totalSessions,
    SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS presentCount,
    SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) AS absentCount,
    SUM(CASE WHEN status = 'Late' THEN 1 ELSE 0 END) AS lateCount,
    (SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) / COUNT(*)) * 100 AS attendancePercentage
FROM attendance
GROUP BY undergraduateId, courseCode;