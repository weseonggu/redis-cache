package com.example.redis;

import com.example.redis.domain.Item;
import com.example.redis.domain.ItemDto;
import com.example.redis.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Cacheable(cacheNames = "itemCache", key = "args[0]")
    public ItemDto create(ItemDto dto) {
        return ItemDto.fromEntity(itemRepository.save(Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build()));
    }

    @Cacheable(cacheNames = "itemAllCache", key = "methodName")
    public List<ItemDto> readAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDto::fromEntity)
                .toList();
    }

    // 이 메서드의 결과는 캐싱이 가능하다.
    // cacheNames: 요 메서드로 인해서 만들어질 캐시를 지침하는 이름
    // key: arge[0]의 의미는 메소드의 0번째 매개변수를 인자로 사용할 거다 여기서는 Long id에 들어오는 값이 키가 됨 캐시 데이터를 구분하기 위해 활용 되는 값
    @Cacheable(cacheNames = "itemCache", key = "args[0]")
    public ItemDto readOne(Long id) {
        return itemRepository.findById(id)
                .map(ItemDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @CachePut(cacheNames = "itemCache", key = "args[0]")
    @CacheEvict(cacheNames = "itemAllCache", allEntries = true)// itemAllCache로 시작하는 모든데이터를 지우겠다는 의미
    public ItemDto update(Long id, ItemDto dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        return ItemDto.fromEntity(itemRepository.save(item));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "itemAllCache", allEntries = true),
            @CacheEvict(cacheNames = "itemCache", key = "#id")
    })
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
    @Cacheable(
            cacheNames = "itemSearchCache",
            key = "{ args[0], args[1].pageNumber, args[1].pageSize }"
    )
    public Page<ItemDto> searchByName(String query, Pageable pageable) {
        return itemRepository.findAllByNameContains(query, pageable)
                .map(ItemDto::fromEntity);
    }
}
