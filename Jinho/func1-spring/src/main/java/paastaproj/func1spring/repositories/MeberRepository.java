package paastaproj.func1spring.repositories;

import paastaproj.func1spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MeberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
