-- Создание таблицы Post
CREATE TABLE if not exists Post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Уникальный идентификатор поста
    name VARCHAR(255) NOT NULL,           -- Название поста
    description TEXT,                     -- Описание поста
    likeCounter INT DEFAULT 0,           -- Счётчик лайков
    imageURL VARCHAR(512),               -- URL изображения
    createDateTime TIMESTAMP NOT NULL   -- Дата и время создания поста
);

-- Создание таблицы Tag
CREATE TABLE  if not exists Tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Уникальный идентификатор тега
    name VARCHAR(255) NOT NULL,           -- Название тега
    post_id BIGINT NOT NULL,              -- Ссылка на пост (внешний ключ)
    FOREIGN KEY (post_id) REFERENCES Post(id) ON DELETE CASCADE -- Связь с таблицей Post
);

-- Создание таблицы Commentary
CREATE TABLE  if not exists Commentary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Уникальный идентификатор комментария
    text TEXT NOT NULL,                   -- Текст комментария
    createDateTime TIMESTAMP NOT NULL,  -- Дата и время создания комментария
    post_id BIGINT NOT NULL,              -- Ссылка на пост (внешний ключ)
    FOREIGN KEY (post_id) REFERENCES Post(id) ON DELETE CASCADE -- Связь с таблицей Post
);


-- Добавление постов
INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime)
VALUES ('First Post', 'Description of the first post', 10, 'https://cdn.dlcompare.com/others_jpg/upload/news/image/v-cyberpunk-2077-mojno-igrat-kak-v-gta-no-ne-bez-posledstviy-image-116915b0a.jpeg.webp', CURRENT_TIMESTAMP);

INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime)
VALUES ('Second Post', 'Description of the second post', 3, 'https://cdn.dlcompare.com/others_jpg/upload/news/image/v-cyberpunk-2077-mojno-igrat-kak-v-gta-no-ne-bez-posledstviy-image-116915b0a.jpeg.webp', CURRENT_TIMESTAMP);

-- Добавление тегов для первого поста
INSERT INTO Tag (name, post_id) VALUES ('Technology', 1);
INSERT INTO Tag (name, post_id) VALUES ('Science', 1);

-- Добавление тегов для второго поста
INSERT INTO Tag (name, post_id) VALUES ('AI', 2);
INSERT INTO Tag (name, post_id) VALUES ('Science', 2);

-- Добавление комментариев к первому посту
INSERT INTO Commentary (text, createDateTime, post_id)
VALUES ('Great post!', CURRENT_TIMESTAMP, 1);

INSERT INTO Commentary (text, createDateTime, post_id)
VALUES ('Very informative.', CURRENT_TIMESTAMP, 1);