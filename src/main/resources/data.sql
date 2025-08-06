-- 설문 삽입
INSERT INTO survey (title, sub_title)
VALUES ('만족도 수요조사', '이 설문은 프로그램에 대한 만족도를 조사하기 위한 것입니다.');
SET @survey_id = LAST_INSERT_ID();

-- 질문 삽입
INSERT INTO question (survey_id, text, sequence)
VALUES
    (@survey_id, '내용이 잘 이해 되었나요?', 1);

-- 각 질문 ID 저장
-- 첫 번째 질문 ID
SET @question1_id = LAST_INSERT_ID();

INSERT INTO question (survey_id, text, sequence)
VALUES
    (@survey_id, '향후 유사한 프로그램이 있다면 참여 의향이 있으신가요?', 2);

SET @question2_id = LAST_INSERT_ID();


-- 선택지 삽입 (첫 번째 질문에 대한 선택지 예시)
INSERT INTO choice (question_id, text, sequence)
VALUES
    (@question1_id, '매우 그렇지않다', 1),
    (@question1_id, '그렇지 않다', 2),
    (@question1_id, '보통이다', 3),
    (@question1_id, '그렇다', 4),
    (@question1_id, '매우 그렇다', 5);

INSERT INTO choice (question_id, text, sequence)
VALUES
    (@question2_id, '매우 그렇지않다', 1),
    (@question2_id, '그렇지 않다', 2),
    (@question2_id, '보통이다', 3),
    (@question2_id, '그렇다', 4),
    (@question2_id, '매우 그렇다', 5);
