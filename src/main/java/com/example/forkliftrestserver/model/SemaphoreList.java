package com.example.forkliftrestserver.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemaphoreList {
    private List<Semaphore> semaphores;

    public List<Semaphore> getSemaphores() {
        return semaphores;
    }

    public void setSemaphores(List<Semaphore> semaphores) {
        this.semaphores = semaphores;
    }

    public boolean isForkliftMovementAllowed(Forklift forklift) {
        Map<Integer, Integer> updates = new HashMap<>();
        int semaphoreIndex = 0;
        for (Semaphore semaphore:
             semaphores) {
            if (semaphore.isForkliftInside(forklift.getCoords())) {
                if (semaphore.getForklift() == null) {
                    updates.put(semaphoreIndex, forklift.getSerialNumber());
                } else if (!(semaphore.getForklift() == forklift)) {
                    return false;
                }
            } else if (semaphore.getForklift() == forklift) {
                updates.put(semaphoreIndex, null);
            }
            semaphoreIndex++;
        }
        for (Integer semaphoreId:
             updates.keySet()) {
            if (updates.get(semaphoreId) == null) {
                semaphores.get(semaphoreId).setForklift(null);
            } else {
                semaphores.get(semaphoreId).setForklift(forklift);
            }
        }
        return true;
    }

    public SemaphoreList() {
    }

    public SemaphoreList(List<Semaphore> semaphores) {
        this.semaphores = semaphores;
    }

    public void addSemaphore(Semaphore semaphore) {
        semaphores.add(semaphore);
    }
}
