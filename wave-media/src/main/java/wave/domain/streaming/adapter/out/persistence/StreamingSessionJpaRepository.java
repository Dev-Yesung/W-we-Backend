package wave.domain.streaming.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.media.domain.entity.StreamingSession;

public interface StreamingSessionJpaRepository extends JpaRepository<StreamingSession, Long> {
}
