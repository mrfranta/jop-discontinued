package cz.zcu.kiv.jop.ioc.guice;

import org.jmock.Mockery;

import com.google.inject.AbstractModule;

/**
 * Google Guice module which supports binding of classes to their mocks created by {@link Mockery}.
 * All modules used for tests should extend this module.
 *
 * @author Mr.FrAnTA
 */
public abstract class MockModule extends AbstractModule {

  /** Mockery used for mocking in this module. */
  protected final Mockery mockery;

  /**
   * Constructs module with new mockery.
   */
  public MockModule() {
    this(new Mockery());
  }

  /**
   * Constructs module with given mockery.
   *
   * @param mockery the mockery which will be used for mocking.
   */
  public MockModule(Mockery mockery) {
    this.mockery = mockery;
  }

  /**
   * Creates mock of given class type and creates the binding.
   *
   * @param clazz the class type which will be mocked and bound.
   */
  @SuppressWarnings("unchecked")
  protected final void bindMock(Class<?> clazz) {
    bind((Class<Object>)clazz).toInstance(mockery.mock(clazz));
  }

  /**
   * Creates mock of given class types and creates the binding for them.
   *
   * @param classes the class types which will be mocked and bound.
   */
  @SuppressWarnings("unchecked")
  protected final void bindMocks(Class<?>... classes) {
    for (Class<?> clazz : classes) {
      bind((Class<Object>)clazz).toInstance(mockery.mock(clazz));
    }
  }

  /**
   * Returns mockery used for mocking in this module.
   *
   * @return Mockery which is used for mocking.
   */
  public Mockery getMockery() {
    return mockery;
  }
}
