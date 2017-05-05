package resources;

public class ResourceServer implements ResourceServerI {
    private TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    @Override
    public void setResource(TestResource resource) {
        this.testResource = resource;
    }

    @Override
    public String getName() {
        return testResource.getName();
    }

    @Override
    public int getAge() {
        return testResource.getAge();
    }
}
