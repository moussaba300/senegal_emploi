package senegal.chercher.emploi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CandidatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Candidat getCandidatSample1() {
        return new Candidat().id(1L).cv("cv1").telephone("telephone1").adresse("adresse1").photoPath("photoPath1");
    }

    public static Candidat getCandidatSample2() {
        return new Candidat().id(2L).cv("cv2").telephone("telephone2").adresse("adresse2").photoPath("photoPath2");
    }

    public static Candidat getCandidatRandomSampleGenerator() {
        return new Candidat()
            .id(longCount.incrementAndGet())
            .cv(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .adresse(UUID.randomUUID().toString())
            .photoPath(UUID.randomUUID().toString());
    }
}
