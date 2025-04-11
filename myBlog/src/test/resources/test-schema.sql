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
