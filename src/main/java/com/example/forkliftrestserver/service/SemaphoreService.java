package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.Semaphore;
import com.example.forkliftrestserver.model.SemaphoreList;
import org.springframework.stereotype.Service;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.net.URL;
import java.util.List;

@Service
public class SemaphoreService {
    private SemaphoreList semaphoreList;

    public SemaphoreService() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource("static/config.yml");
        Constructor semaphoreListConstructor = new Constructor(SemaphoreList.class);
        TypeDescription semaphoreDescription = new TypeDescription(Semaphore.class);
        semaphoreDescription.addPropertyParameters("semaphores", Semaphore.class, Object.class);
        semaphoreListConstructor.addTypeDescription(semaphoreDescription);
        Yaml yaml = new Yaml();
        try {
            semaphoreList = yaml.load(resource.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getPermission(Forklift forklift) {
        return semaphoreList.isForkliftMovementAllowed(forklift);
    }

    public List<Semaphore> getSemaphoreList() {
        return semaphoreList.getSemaphores();
    }
}
