

#include "jace/proxy/types/JShort.h"

#ifndef JACE_JCLASS_IMPL_H
#include "jace/JClassImpl.h"
#endif
using jace::JClassImpl;

#include "jace/BoostWarningOff.h"
#include <boost/thread/mutex.hpp>
#include "jace/BoostWarningOn.h"

BEGIN_NAMESPACE_3( jace, proxy, types )


JShort::JShort( jvalue value )
{
  setJavaJniValue( value );
}

JShort::JShort( jshort short_ )
{
  jvalue value;
  value.s = short_;
  setJavaJniValue( value );
}

JShort::~JShort()
{}

JShort::operator jshort() const
{
  return getJavaJniValue().s;
}

jshort JShort::getShort() const
{
  return getJavaJniValue().s;
}

bool JShort::operator==( const JShort& short_ ) const
{
  return short_.getShort() == getShort();
}

bool JShort::operator!=( const JShort& short_ ) const
{
  return !( *this == short_ );
}

bool JShort::operator==( jshort val ) const
{
  return val == getShort();
}

bool JShort::operator!=( jshort val ) const
{
  return ! ( *this == val );
}

static boost::mutex javaClassMutex;
const JClass& JShort::staticGetJavaJniClass() throw ( JNIException )
{
	static boost::shared_ptr<JClassImpl> result;
	boost::mutex::scoped_lock lock(javaClassMutex);
	if (result == 0)
		result = boost::shared_ptr<JClassImpl>(new JClassImpl("short", "S"));
	return *result;
}

const JClass& JShort::getJavaJniClass() const throw ( JNIException )
{
  return JShort::staticGetJavaJniClass();
}


END_NAMESPACE_3( jace, proxy, types )

