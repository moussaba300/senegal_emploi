package senegal.chercher.emploi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OffreEmploiTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OffreEmploi getOffreEmploiSample1() {
        return new OffreEmploi().id(1L).titre("titre1").description("description1");
    }

    public static OffreEmploi getOffreEmploiSample2() {
        return new OffreEmploi().id(2L).titre("titre2").description("description2");
    }

    public static OffreEmploi getOffreEmploiRandomSampleGenerator() {
        return new OffreEmploi()
            .id(longCount.incrementAndGet())
            .titre(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
