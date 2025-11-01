package io.github.magwas.coder.conversation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationStateRepository extends CrudRepository<ConversationStateData, String> {}
